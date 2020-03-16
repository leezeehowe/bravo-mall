# JAVA操作树（自关联型数据库表）
数据库中，自关联性表结构设计是很常见的，如电商系统中的类目设计、权限系统中的角色设计、资源设计。

## 自关联型表结构
为了更好的理解，褪去一些业务上的东西，自关联型表结构，最基础的几个字段如下：
```mysql
create table tree_node (
    # 元组主键，也即当前节点的id，没什么好说的吧...
    id bigint primary key ,
    # 当前节点的父节点的id，当前节点就靠这个挂在树支上哦
    parId bigint
)
```
添上一些额外的附属描述信息
```mysql
create table tree_node (
    # ...省略上述字段

    # 所在树的层级，0代表树根，其实就是深度-1，至于为什么0开头，因为代码的世界所有`index`就是0开头嘛...
    level int,
    # 该节点的子孙节点数量
    sub_count int
)
```
这样就形成一颗树了，依靠节点中的`parId`做关联，就像链表一样串起来了，链表是靠`next_node`字段存储其下一节点的内存地址，这里是靠节点的`parId`存储其上一节点的`id`，反过来了。

## 结合业务：Spring Boot + Mybatis Plus
假设一个权限管理系统中，角色、资源都是自关联型的，那我们每个Service、entity都重复写一遍关于树形结构的代码嘛？这是不可能的，所以面向对象、抽象的思想就很重要了。

### TreeNode接口
`Java Web`项目，普通都有`dao`层中的`entity`实体类吧（对应数据库表），每个自关联型表结构的实体类都有上述的几个字段，所以我们把它抽象出来一个`interface`接口。

```java
/**
 * 节点
 */
public interface TreeNode {

    Long getId();

    Integer getLevel();

    Long getParId();

    Integer getSubCount();

    void setSubCount(Integer subCount);

}
```

### ITreeService 接口
抽象出来一个接口有什么用呢？那就非常重要了，我们就可以把关于操作树形结构相关的逻辑从业务代码上剥离出来了。
`Spring Boot`项目，二话不说先来一个`Service`层接口。
```java
public interface ITreeService<T extends TreeNode> {

    /**
     * 获取参数给定的层级之上所有节点并按层级分组，返回结果不包括参数所给层级。
     *
     * @param theLevel 所给层级
     * @return 图： Key=层级 value=该层级的所有节点，
     * 注意：返回的图的结果是按层级降序的，即假设所给层级是5，则返回的map的key：4 3 2 1 0 如此。
     */
    Map<Integer, List<T>> getAllNodeUponTheSpecifiedLevel(int theLevel, IService<T> service);

    /**
     * 沿着路径头的父节点ID向上得到一条往树根方向的路径，
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
```
### ITreeServiceImpl 实现类
```java
@Service
public class TreeServiceImpl<T extends TreeNode> implements ITreeService<T> {

    @Override
    public Map<Integer, List<T>> getAllNodeUponTheSpecifiedLevel(int theLevel, IService<T> service) {
        Map<Integer, List<T>> map = new HashMap<>();
        do {
            QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
            queryWrapper.eq("level", --theLevel);
            map.put(theLevel, service.list(queryWrapper));
        } while (theLevel>0);
        return map;
    }

    @Override
    public List<T> getPathToRootFrom(T currentNode, IService<T> service) {
        // 结果：路径上的节点
        List<T> nodeOfThePath;
        // 层级及其对应节点
        Map<Integer, List<T>> levelMap;
        // 当前节点的层级
        int level;
        // 当前节点的父节点id
        Long parId;
        nodeOfThePath = new ArrayList<>();
        level = currentNode.getLevel();
        parId = currentNode.getParId();
        levelMap = this.getAllNodeUponTheSpecifiedLevel(level, service);
        // 遍历层级
        for (int i = level - 1; i >= 0; i--) {
            // 遍历层级的节点
            List<T> nodeList = levelMap.get(i);
            for (T node : nodeList) {
                // 找到当前节点的父节点了
                if (node.getId() == parId) {
                    // 保存到路径队列中
                    nodeOfThePath.add(node);
                    // 更新父节点id
                    parId = node.getParId();
                    // 结束遍历层级的节点
                    break;
                }
            }
        }
        return nodeOfThePath;
    }

    @Override
    public List<T> increaseSubCountOfThePath(T headOfThePath, int increment, IService<T> service, boolean saveOrNot) {
        List<T> path = this.getPathToRootFrom(headOfThePath, service);
        path = path.stream().map((node) -> {
            node.setSubCount(node.getSubCount() + increment);
            return node;
        }).collect(Collectors.toList());
        if(saveOrNot) {
            service.saveOrUpdateBatch(path, path.size());
        }
        return path;
    }

}
```














