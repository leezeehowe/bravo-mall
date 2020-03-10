package per.lee.bravo.mall.authorization.controller;


import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/role")
public class RoleController {

    @GetMapping("/{ids}")
    public List<RoleVo> get(@PathVariable String ids) {
        String[] id = ids.split(",");
        List<RoleVo> roleVoList = new ArrayList<>();
        for (String s : id) {
            roleVoList.add(new RoleVo(Long.valueOf(s), s));
        }
        return roleVoList;
    }

}
