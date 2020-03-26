package per.lee.bravo.mall.authorization.vo;

import per.lee.bravo.mall.authorization.tree.po.PayloadNode;
import per.lee.bravo.mall.authorization.tree.po.TreeNode;

public class IViewTreeNode<T extends TreeNode> extends PayloadNode<T> {

    public IViewTreeNode(T payload) {
        super(payload);
    }

}
