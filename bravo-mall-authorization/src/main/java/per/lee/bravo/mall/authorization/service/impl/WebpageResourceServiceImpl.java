package per.lee.bravo.mall.authorization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import per.lee.bravo.mall.authorization.constant.operationError.OperationErrorEnum;
import per.lee.bravo.mall.authorization.entity.Authority;
import per.lee.bravo.mall.authorization.entity.User;
import per.lee.bravo.mall.authorization.entity.WebpageResource;
import per.lee.bravo.mall.authorization.mapper.WebpageResourceMapper;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;
import per.lee.bravo.mall.authorization.tree.po.PayloadNode;
import per.lee.bravo.mall.authorization.tree.service.ITreeService;
import per.lee.bravo.mall.authorization.service.IUserService;
import per.lee.bravo.mall.authorization.service.IWebpageResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 网页资源表 服务实现类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@Service
public class WebpageResourceServiceImpl extends ServiceImpl<WebpageResourceMapper, WebpageResource> implements IWebpageResourceService {

    @Autowired
    IUserService userService;
    @Autowired
    ITreeService<WebpageResource> treeService;

    @Override
    public WebpageResource getByName(String name) {
        QueryWrapper<WebpageResource> queryWrapper = new QueryWrapper<WebpageResource>()
                .eq("name", name);
        return getOne(queryWrapper);
    }

    @Override
    @Transactional()
    public WebpageResource postOne(WebpageResource dto) throws BravoApiException {
        User creator = Optional.ofNullable(userService.getRequestingUser(true)).orElseThrow(() -> new BravoApiException(OperationErrorEnum.CREATE_ENTITY_FAIL, "无法获取当前请求用户的相关信息"));
        dto.setCreateBy(creator.getId());
        dto.setUpdateBy(creator.getId());
        if(this.save(dto)) {
            // 增加其父节点的子节点数量
            treeService.increaseSubCountOfThePath(dto, 1, this, true);
            return this.getByName(dto.getName());
        }
        else {
            throw new BravoApiException(OperationErrorEnum.CREATE_ENTITY_FAIL, "创建资源失败，请重试");
        }
    }

    @Override
    public List<WebpageResource> getByParId(Long parId) {
        QueryWrapper<WebpageResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("par_id", parId);
        return this.list(queryWrapper);
    }

    @Override
    public List<PayloadNode<WebpageResource>> getAllRootTree() {
        List<PayloadNode<WebpageResource>> treeList = new ArrayList<>();
        List<WebpageResource> rootNodeList = getByParId(0L);
        rootNodeList.forEach(rootNode -> {
            PayloadNode<WebpageResource> tree = this.treeService.getSubTreeOf(rootNode, this);
            treeList.add(tree);
        });
        return treeList;
    }

    @Override
    public List<WebpageResource> getWebpageOfAuthority(List<Authority> authorityList) {
        QueryWrapper<WebpageResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name");
        authorityList.forEach(authority -> {
            queryWrapper.eq("id", authority.getResourceId()).or();
        });
        return list(queryWrapper);
    }


}
