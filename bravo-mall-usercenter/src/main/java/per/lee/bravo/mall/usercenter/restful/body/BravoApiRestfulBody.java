package per.lee.bravo.mall.usercenter.restful.body;

import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import per.lee.bravo.mall.usercenter.restful.body.errors.ErrorItem;
import per.lee.bravo.mall.usercenter.restful.body.meta.Meta;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BravoApiRestfulBody {

    // 请求id
    private String request_id;
    // 服务提供方
    private String server;
    // 响应时间
    private String server_time;
    // 元数据
    private Meta meta;
    // 资源
    private Object resource;
    // 异常
    private ErrorItem exception;

    public BravoApiRestfulBody(String request_id, String server, String server_time, Meta meta) {
        this.request_id = request_id;
        this.server = server;
        this.server_time = server_time;
        this.meta = meta;
    }

}