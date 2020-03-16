package per.lee.bravo.mall.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.lee.bravo.mall.authorization.tree.service.ITreeService;

@RestController
@RequestMapping("/${api.version}/tree")
public class TreeController {

    @Autowired
    ITreeService<?> treeService;

    @GetMapping("/level/{table}")
    public Integer deepestLevel(@PathVariable String table) {
        // todo 暂时不考虑安全，日后需要添加一个枚举类，检查参数table是否合法且能够查询深度，防止sql注入。
        return treeService.getDeepestLevel(table);
    }

}
