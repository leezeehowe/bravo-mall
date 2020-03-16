package per.lee.bravo.mall.authorization.tree;

import com.baomidou.mybatisplus.extension.service.IService;
import per.lee.bravo.mall.authorization.tree.po.TreeNode;

import java.util.List;
import java.util.Map;

public interface ITreeService<T extends TreeNode> {

    /**
     * 获取指定表的最大深度
     * @param tableName 表名
     * @return 最大深度-1
     */
    Integer getDeepestLevel(String tableName);

    /**
     * 获取参数给定的层级之上所有节点并按层级分组，返回结果不包括参数所给层级。
     *
     * @param theLevel 所给层级
     * @return 图： Key=层级 value=该层级的所有节点，
     * 注意：返回的图的结果是按层级降序的，即假设所给层级是5，则返回的map的key：4 3 2 1 0 如此。
     */
    Map<Integer, List<T>> getAllNodeUponTheSpecifiedLevel(int theLevel, IService<T> service);

    /**
     * 沿着路径头（子节点）的父节点ID向上得到一条往树根方向的路径，
     *
     * @param headOfThePath 路径头
     * @return 路径
     * 注意：返回的路径不包括路径头，向上的途中若遇到层级无节点或找不到父节点则路径中断
     */
    List<T> getPathToRootFrom(T headOfThePath, IService<T> service);



    /**
     * 把根据给定路径头检索得到的路径上的所有节点（不包括路径头）的子节点数量添上增量。
     *
     * @param headOfThePath 路径头，即最深的子节点
     * @param increment     subCount增量
     * @param saveOrNot     是否需要保存到数据库
     * @return 添上增量后的路径
     */
    List<T> increaseSubCountOfThePath(T headOfThePath, int increment, IService<T> service, boolean saveOrNot);
}
