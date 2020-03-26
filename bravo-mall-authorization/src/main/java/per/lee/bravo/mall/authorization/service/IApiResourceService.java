package per.lee.bravo.mall.authorization.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import per.lee.bravo.mall.authorization.entity.ApiResource;
import com.baomidou.mybatisplus.extension.service.IService;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;
import per.lee.bravo.mall.authorization.tree.po.PayloadNode;

import java.util.List;

/**
 * <p>
 * 接口资源表 服务类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
public interface IApiResourceService extends IService<ApiResource> {

    /**
     * 创建一个api资源
     * @param apiResource 实体类
     */
    void createApiResource(ApiResource apiResource) throws BravoApiException;

    /**
     * 查找API资源
     * @param name 英文标识
     * @param belongTo 所属服务名
     * @param version 版本
     * @return
     */
    ApiResource getOne(String name, String belongTo, String version);

    /**
     * 分页
     * @param current
     * @param size
     * @return
     */
    IPage<ApiResource> pageApiResource(Integer current, Integer size);

    List<PayloadNode<ApiResource>> getAllNodeTree();
}
