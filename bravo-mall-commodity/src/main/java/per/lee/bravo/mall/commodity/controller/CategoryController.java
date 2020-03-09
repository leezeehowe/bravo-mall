package per.lee.bravo.mall.commodity.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.lee.bravo.mall.commodity.entity.Category;
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
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Api(value="")
    @GetMapping("/page")
    public IPage<Category> list(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize) {
        IPage<Category> page = new Page<>();
        page.setCurrent(pageNo).setSize(pageSize);
        return categoryService.page(page);
    }
}
