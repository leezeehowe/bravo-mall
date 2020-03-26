package per.lee.bravo.mall.authorization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import per.lee.bravo.mall.authorization.entity.Authority;
import per.lee.bravo.mall.authorization.entity.WebpageResource;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;
import per.lee.bravo.mall.authorization.tree.po.PayloadNode;

import java.util.List;

/**
 * <p>
 * 网页资源表 服务类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
public interface IWebpageResourceService extends IService<WebpageResource> {

    /**
     * 根据名字获取
     * @param name 资源名
     * @return
     */
    WebpageResource getByName(String name);

    /**
     * 创建一个页面资源
     * @param dto 页面资源参数
     * @return
     */
    WebpageResource postOne(WebpageResource dto) throws BravoApiException;

    /**
     * 根据上级id获取
     * @param parId 上级id
     * @return
     */
    List<WebpageResource> getByParId(Long parId);

    /**
     * 获取所有根节点子树
     * @return
     */
    List<PayloadNode<WebpageResource>> getAllRootTree();

    /**
     * 根据页面资源权限获取页面资源
     * @param authorityList
     * @return
     */
    List<WebpageResource> getWebpageOfAuthority(List<Authority> authorityList);

}
