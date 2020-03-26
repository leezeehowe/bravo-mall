package per.lee.bravo.mall.authorization.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import per.lee.bravo.mall.authorization.entity.ApiResource;
import per.lee.bravo.mall.authorization.entity.Role;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;
import per.lee.bravo.mall.authorization.service.IApiResourceService;
import per.lee.bravo.mall.authorization.tree.po.PayloadNode;
import per.lee.bravo.mall.authorization.tree.service.ITreeService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 接口资源表 前端控制器
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/${api.version}/api-resource")
public class ApiResourceController {

    @Autowired
    private IApiResourceService apiResourceService;

    @PostMapping
    public ApiResource createApiResource(@RequestBody ApiResource dto) throws BravoApiException {
        apiResourceService.createApiResource(dto);
        return apiResourceService.getOne(dto.getName(), dto.getBelongTo(), dto.getVersion());
    }

    @GetMapping("/page")
    public IPage<ApiResource> pageApiResource(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "1000") Integer size) {
        return apiResourceService.pageApiResource(current, size);
    }

    @GetMapping("/tree/all")
    public List<PayloadNode<ApiResource>> getAllNodeTree() {
        return apiResourceService.getAllNodeTree();
    }

}
