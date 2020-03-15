package per.lee.bravo.mall.authorization.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import per.lee.bravo.mall.authorization.entity.Role;
import per.lee.bravo.mall.authorization.service.IRoleService;
import per.lee.bravo.mall.authorization.vo.RoleVo;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/${api.version}/role")
public class RoleController {

    @Autowired
    IRoleService roleService;

    @GetMapping("/page")
    public IPage<Role> get(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "8") Integer size,
            @RequestParam(defaultValue = "0") Integer level,
            @RequestParam(defaultValue = "0") Integer id,
            @RequestParam(defaultValue = "-1") Integer parId,
            @RequestParam(defaultValue = "0") Integer status) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        IPage<Role> page = new Page<>();
        page.setCurrent(current).setSize(size);
        if(!id.equals(0)) {
            queryWrapper.eq("id", id);
        }
        if(!parId.equals(-1)) {
            queryWrapper.eq("par_id", parId);
        }
        queryWrapper
                .eq("level", level)
                .eq("status", status);
        return roleService.page(page, queryWrapper);
    }

    @GetMapping("/deepestLevel")
    public Integer deepestLevel() {
        return roleService.getDeepestLevel();
    }

}
