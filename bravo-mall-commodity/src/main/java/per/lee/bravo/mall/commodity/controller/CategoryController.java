package per.lee.bravo.mall.commodity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.lee.bravo.mall.commodity.entity.Category;
import per.lee.bravo.mall.commodity.restful.protocol.BravoApiException;
import per.lee.bravo.mall.commodity.service.ICategoryService;

/**
 * <p>
 * 类目表 前端控制器
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-08
 */
@RestController
@RequestMapping("/${api.version}/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * 分页获取类目
     * @return
     */
    @GetMapping("/page")
    public IPage<Category> page(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "0") Long parId,
            @RequestParam(defaultValue = "0") int level) {
        IPage<Category> page = new Page<>(current, size);
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("par_id", parId).eq("level", level);
        return categoryService.page(page, queryWrapper);
    }

    @PostMapping
    public Category create(@RequestBody Category dto) throws BravoApiException {
        return categoryService.createOne(dto);
    }

}
