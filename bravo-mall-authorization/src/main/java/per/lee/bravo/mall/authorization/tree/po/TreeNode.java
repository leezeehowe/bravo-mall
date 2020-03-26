package per.lee.bravo.mall.authorization.tree.po;

/**
 * 节点
 */
public interface TreeNode {

    Long getId();

    Integer getLevel();

    Long getParId();

    Integer getSubCount();

    void setSubCount(int subCount);

}
