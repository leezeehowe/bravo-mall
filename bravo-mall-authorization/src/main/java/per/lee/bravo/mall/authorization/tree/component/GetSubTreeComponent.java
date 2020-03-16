package per.lee.bravo.mall.authorization.tree.component;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import per.lee.bravo.mall.authorization.tree.po.PayloadNode;
import per.lee.bravo.mall.authorization.tree.po.TreeNode;
import per.lee.bravo.mall.authorization.tree.service.ITreeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取子树方法类
 */
@Component
public class GetSubTreeComponent<T extends TreeNode> {

    @Autowired
    private ITreeService<T> treeService;

    private PayloadNode<T> root;

    private Integer deepestLevel;

    // 在树根节点所在层级之下的所有节点
    private Map<Integer, List<T>> allNode;

    /**
     * 初始化
     * @param rootOfTheTree 要获取子树的根节点
     * @param deepestLevel 限定的层级范围，即把此层级当作叶子节点层
     * @param businessService Mybatis-Plus业务类，用于查询数据库
     */
    public void init(T rootOfTheTree, int deepestLevel, IService<T> businessService) {
        // 初始化所有节点
        this.allNode = treeService.getAllNodeBetweenSpecifiedLevel(rootOfTheTree.getLevel(), deepestLevel, businessService);
        this.root = new PayloadNode<>(rootOfTheTree);
        this.deepestLevel = deepestLevel;
    }

    /**
     * init初始化根节点 root 后，执行此方法，生成root的子树
     * @return
     */
    public PayloadNode<T> getSubTree() {
        getChildren(this.root);
        return this.root;
    }

    /**
     * 给 {@param rootOfTheTree}添上其叶子节点
     * @param rootOfTheTree 树根节点
     */
    private void getChildren(PayloadNode<T> rootOfTheTree) {
        List<PayloadNode<T>> childrenList = new ArrayList<>();
        // 根id
        Long rootId = rootOfTheTree.getPayload().getId();
        // 根层级
        Integer rootLevel = rootOfTheTree.getPayload().getLevel();
        // 判断当前根节点是否已经到指定层级了
        if(this.deepestLevel.equals(rootLevel)) return;
        // 遍历树根的下一层级寻找其子节点
        for (T t : this.allNode.get(rootLevel + 1)) {
            // 找到子节点，加入子节点列表
            if(t.getParId().equals(rootId)) {
                PayloadNode<T> payloadNode = new PayloadNode<>(t);
                getChildren(payloadNode);
                childrenList.add(payloadNode);
            }
        }
        rootOfTheTree.setChildren(childrenList);
    }

}
