package per.lee.bravo.mall.gateway.bean;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * 从nacos-配置中心中读取需要跳过token验证的url列表
 * 并对request进行检查此次请求是否无需验证token
 * 匹配项：
 * 1. 请求的path，如 /bravo-sms/sms，区分大小写，区分结尾是否带 斜杠/，如 /info 和 /info/ 属于两个不同的path
 * 2. 请求的方法，如 get、post，不区分大小写
 * @author leezeehowe
 */
@Slf4j
public class SkipTokenInspectionUrlBean {

    private final String DATE_ID = "needless_inspection_token_url_list";

    private final String GROUP = "BRAVO_MALL";

    private ConfigService configService;

    @Getter // 具体的数据
    private List<UrlItem> urlList;

    public SkipTokenInspectionUrlBean(String configServerAddress) throws NacosException {
        init(configServerAddress);
        loadData();
        watch();
    }

    /**
     * 初始化，连接配置中心
     * @param configServerAddress 配置中心地址
     * @throws NacosException 连接异常
     */
    private void init(String configServerAddress) throws NacosException {
        configService = NacosFactory.createConfigService(configServerAddress);
    }

    /**
     * 读取配置
     * @throws NacosException 异常
     */
    private void loadData() throws NacosException {
        setUrlList(configService.getConfig(DATE_ID, GROUP, 5000));
    }

    /**
     * 监听配置变化
     * @throws NacosException 异常
     */
    private void watch() throws NacosException {
        configService.addListener(DATE_ID, GROUP, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                setUrlList(configInfo);
                log.info("刷新配置");
            }
            @Override
            public Executor getExecutor() {
                return null;
            }
        });
    }

    public boolean skipTokenInspection(ServerHttpRequest request) {
        String requestPath = request.getPath().toString();
        HttpMethod requestMethod = request.getMethod();
        if(requestMethod == null) return false;
        UrlItem urlItem = new UrlItem(requestPath, requestMethod.name());
        return getUrlList().contains(urlItem);
    }

    private void setUrlList(String data) {
        this.urlList = JSONUtil.parseArray(data).toList(UrlItem.class);
    }

}

class UrlItem {

    public String url;
    public String method;
    public String function;

    public UrlItem(String url, String method) {
        this.url = url;
        this.method = method;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  UrlItem) {
            UrlItem urlItem = (UrlItem) obj;
            return StrUtil.equals(urlItem.url, this.url) && StrUtil.equals(urlItem.method, this.method, true);
        }
        else {
            return false;
        }
    }
}
