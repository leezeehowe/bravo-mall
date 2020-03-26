package per.lee.bravo.mall.authorization.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import per.lee.bravo.mall.authorization.constant.operationError.OperationErrorEnum;
import per.lee.bravo.mall.authorization.dto.PostWebpageResourceDto;
import per.lee.bravo.mall.authorization.entity.Role;
import per.lee.bravo.mall.authorization.entity.WebpageResource;
import per.lee.bravo.mall.authorization.mapper.TreeMapper;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;
import per.lee.bravo.mall.authorization.service.IWebpageResourceService;
import per.lee.bravo.mall.authorization.tree.po.PayloadNode;
import per.lee.bravo.mall.authorization.tree.service.ITreeService;

import java.util.List;

/**
 * <p>
 * 网页资源表 前端控制器
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/${api.version}/webpage-resource")
public class WebpageResourceController {

    @Autowired
    IWebpageResourceService webpageResourceService;
    @Autowired
    ITreeService<WebpageResource> treeService;

    @GetMapping("/page")
    public IPage<WebpageResource> get(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "8") Integer size,
            @RequestParam(defaultValue = "0") String level,
            @RequestParam(defaultValue = "0") Integer id,
            @RequestParam(defaultValue = "-1") Integer parId) {
        QueryWrapper<WebpageResource> queryWrapper = new QueryWrapper<>();
        IPage<WebpageResource> page = new Page<>(current, size);
        if(!id.equals(0)) {
            queryWrapper.eq("id", id);
        }
        if(!parId.equals(-1)) {
            queryWrapper.eq("par_id", parId);
        }
        for (String levelStr : level.split(",")) {
            queryWrapper
                    .eq("level", levelStr)
                    .or();
        }
        return webpageResourceService.page(page, queryWrapper);
    }

    @PostMapping
    public WebpageResource create(@RequestBody WebpageResource dto) throws BravoApiException {
        return webpageResourceService.postOne(dto);
    }

    @GetMapping
    public Integer deepestLevel() {
        return treeService.getDeepestLevel("webpage_resource");
    }

    @GetMapping("tree/all")
    public List<PayloadNode<WebpageResource>> getAllRootTree() {
        return webpageResourceService.getAllRootTree();
    }

}
