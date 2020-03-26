package per.lee.bravo.mall.authorization.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import per.lee.bravo.mall.authorization.constant.operationError.OperationErrorEnum;
import per.lee.bravo.mall.authorization.entity.ApiResource;
import per.lee.bravo.mall.authorization.entity.User;
import per.lee.bravo.mall.authorization.mapper.ApiResourceMapper;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;
import per.lee.bravo.mall.authorization.service.IApiResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import per.lee.bravo.mall.authorization.service.IUserService;
import per.lee.bravo.mall.authorization.tree.po.PayloadNode;
import per.lee.bravo.mall.authorization.tree.service.ITreeService;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 接口资源表 服务实现类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@Service
public class ApiResourceServiceImpl extends ServiceImpl<ApiResourceMapper, ApiResource> implements IApiResourceService {

    @Autowired
    private IUserService userService;
    @Autowired
    private ITreeService<ApiResource> treeService;

    @Override
    public void createApiResource(ApiResource apiResource) throws BravoApiException {
        String name = apiResource.getName();
        String version = apiResource.getVersion();
        String belongTo = apiResource.getBelongTo();
        // 查重
        if(getOne(name, belongTo, version) != null) {
            throw new BravoApiException(OperationErrorEnum.CONFLICT_ENTITY_ERROR,
                    StrUtil.format("该API资源[name={} version={} belongTo={}]已存在，请勿重复创建", name, version, belongTo));
        }
        // 当前请求用户
        User requestingUser = userService.getRequestingUser(true);
        apiResource.setCreateBy(requestingUser.getId());
        apiResource.setUpdateBy(requestingUser.getUpdateBy());
        try{
            save(apiResource);
        }
        catch (Exception e) {
            throw new BravoApiException(OperationErrorEnum.CREATE_ENTITY_FAIL, "创建API资源失败，请检查参数稍后再试。");
        }
    }

    @Override
    public ApiResource getOne(String name, String belongTo, String version) {
        QueryWrapper<ApiResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name)
                .eq("belong_to", belongTo)
                .eq("version", version);
        return getOne(queryWrapper);
    }

    @Override
    public IPage<ApiResource> pageApiResource(Integer current, Integer size) {
        IPage<ApiResource> page = new Page<>(current, size);
        return page(page);
    }

    @Override
    public List<PayloadNode<ApiResource>> getAllNodeTree() {
        List<PayloadNode<ApiResource>> rootNodeTree = new ArrayList<>();
        // 查出所有parId为0的树根节点
        List<ApiResource> rootNode = treeService.getAllRootNode(this);
        rootNode.forEach(node -> {
            rootNodeTree.add(treeService.getSubTreeOf(node, this));
        });
        return rootNodeTree;
    }
}
