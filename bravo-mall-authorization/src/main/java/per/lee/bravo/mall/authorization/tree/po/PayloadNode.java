package per.lee.bravo.mall.authorization.tree.po;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

/**
 * 带承载功能的树节点
 * @param <T>
 */
public class PayloadNode<T extends TreeNode>{

    /**
     * 所承载的实体类
     */
    @Getter @Setter
    private T payload;

    /**
     * 所承载的实体类的儿子节点
     */
    @Getter @Setter
    private List<PayloadNode<T>> children = new ArrayList<>();

    public PayloadNode(T payload) {
        this.payload = payload;
    }

}
