# 树-数据结构（自关联型表结构）
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

## 结合业务
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

我们规定每个实体类都必须实现该`TreeNode`接口，接着我们编写的通用的算法就可以对其进行操作，所以`interface`也相当于一个**协议**。

该接口的方法也很简单明了，就是需要获取该实体类（也即节点）：

- id：唯一标识
- level：所在层级，也即树的深度-1
- parId：父节点的唯一标识
- subCount：**子孙**节点数量



## PayloadNode 

但是，只有一个`TreeNode`接口，我们无法实现**构造子树**的功能，因为`TreeNdoe`接口不具有**承载**能力，它只描述了节点的**基本元信息**，使用该接口只能构造出一个**没有业务数据的子树**，即只是最基本的一颗靠`id`和`padId`串起来的树，要获取业务数据必须要靠这些`id`再查一边数据库，所以我们需要一个**有承载能力**的接口。

**并且不能破坏我们原有的基于`TreeNode`接口的协议**

```java
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
```

这样就实现了一个**具有承载能力的节点**，规定`泛型T`是一个实现了`TreeNode`接口的类，这样子设计，此前基于`TreeNode接口`协议的算法同样适用于`PayloadNode`，这大概也算**面向扩展开放，面向修改关闭吧**。

目前该类主要用于**构造子树**，`payload`字段是当前节点的业务数据也包括节点元数据，`children`字段是当前节点的子节点，以此类推。

### ITreeService 接口



有了上面的两个**协议**，我们就可以把关于操作树形结构相关的逻辑从业务代码上剥离出来了。只要你的类实现了最基本的`TreeNode`接口，下面的算法就可以使用。

> 下面的算法有一个缺陷，就是跟`Mybatis-Plus`和`Spring IOC`容器紧密耦合了，因为是自关联性表结构基础上的树，所以使用到了`Mybatis Plus`的**CRUD接口**以及使用了`Spring IOC`容器的*自动注入*。
>
> 下面算法的入参中的`IService<T>`即`Mybatis-Plus`的`CRUD`接口，其实这里可以做一个比较简单的解耦就是再抽出一个接口`DbOperationInterface`，算法中需要使用到什么关于数据库CRUD的方法就在`DbOperationInterface`接口里声明即可。

```java
public interface ITreeService<T extends TreeNode> {
}
```

该接口是一个泛型类，要求`泛型T`必须是实现了`TreeNode`接口的。

下面介绍实现的几种常用算法：

### getAllNodeUponTheSpecifiedLevel

```java
    /**
     * 获取参数给定的层级之上的所有节点并按层级分组，返回结果不包括参数所给层级。
     *
     * @param theLevel 所给层级
     * @return 图： Key=层级 value=该层级的所有节点，
     * 注意：返回的图的结果是按层级降序的，即假设所给层级是5，则返回的map的key：4 3 2 1 0 如此。
     */
    Map<Integer, List<T>> getAllNodeUponTheSpecifiedLevel(int theLevel, IService<T> service);
```

```java
    @Override
    public Map<Integer, List<T>> getAllNodeUponTheSpecifiedLevel(int theLevel, 		IService<T> service) {
        Map<Integer, List<T>> map = new HashMap<>();
        do {
            QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
            queryWrapper.eq("level", --theLevel);
            map.put(theLevel, service.list(queryWrapper));
        } while (theLevel>0);
        return map;
    }
```



<img src="https://lee-img-bed.oss-cn-shenzhen.aliyuncs.com/tree/getAllNodeUponTheSpecifiedLevel.PNG" alt="example" style="zoom:50%;" />

假设粉色箭头是`theLevel`，返回结果即按层级（颜色）分组的`theLevel`上层的所有节点。

###  getAllNodeBetweenSpecifiedLevel

```java
	/**
     * 获取指定层级范围之内的所有节点并按层级分组，返回结果不包括beginLevel。
     * @param beginLevel 起始层级，树根端
     * @param endLevel  结束层级， 叶子端
     * @return
     */
    Map<Integer, List<T>> getAllNodeBetweenSpecifiedLevel(int beginLevel, int endLevel, IService<T> service);
```

```java
    @Override
    public Map<Integer, List<T>> getAllNodeBetweenSpecifiedLevel(int beginLevel, int endLevel, IService<T> service) {
        Map<Integer, List<T>> map = new HashMap<>();
        int currentLevel = beginLevel + 1;
        do {
            QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
            queryWrapper.eq("level", currentLevel);
            map.put(currentLevel, service.list(queryWrapper));
            currentLevel++;
        } while (currentLevel <= endLevel);
        return map;
    }
```



<img src="https://lee-img-bed.oss-cn-shenzhen.aliyuncs.com/tree/getAllNodeBetweenSpecifiedLevel.JPG" style="zoom:50%;" />

`beginLevel`是**靠近树根**的那一边，`endLevel`是**靠近叶子节点**的那一边，其实跟上面的方法差不多。

### getPathToRootFrom

```java
    /**
     * 沿着路径头（子节点）的父节点ID向上得到一条往树根方向的路径，
     *
     * @param headOfThePath 路径头
     * @return 路径
     * 注意：返回的路径不包括路径头，向上的途中若遇到层级无节点或找不到父节点则路径中断，但是这种情况基本不太	          可能会出现的，除非数据库出现异常了。
     */
    List<T> getPathToRootFrom(T headOfThePath, IService<T> service);
```

```java
 @Override
    public List<T> getPathToRootFrom(T headOfThePath, IService<T> service) {
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
```

<img src="https://lee-img-bed.oss-cn-shenzhen.aliyuncs.com/tree/getPathToRootFrom.JPG" style="zoom:50%;" />

假设箭头所指是`headOfThePath`，得到的就是绿色的节点组成的路径。

### getSubTreeOf

该方法就是一个**递归算法**（暂时没想到其他方法xx），因为需要递归获取每一个父节点的子节点才能构造一棵完整的子树。

```java
    /**
     * 获取一个以给定节点作为树根的子树
     * @param rootOfTheTree 子树树根
     * @return 路径
     */
    PayloadNode<T> getSubTreeOf(T rootOfTheTree, IService<T> service);
```

```java
    @Override
    public PayloadNode<T>  getSubTreeOf(T rootOfTheTree, IService<T> service) {
        getSubTreeComponent.init(rootOfTheTree, this.getDeepestLevel("webpage_resource"), service);
        return getSubTreeComponent.getSubTree();
    }
```

##### GetSubTreeComponent

这里因为需要用到递归，所以把该方法单独抽离出来做一个**组件**`GetSubTreeComponent`，毕竟还是有一句话叫**最小知识原则**的嘛，哈哈哈。

```java
/**
 * 获取子树方法类
 */
@Component
public class GetSubTreeComponent<T extends TreeNode> {
    @Autowired
    private ITreeService<T> treeService;
	// 子树的树根节点
    private PayloadNode<T> root;
	// 完整的树的最大层级，即深度-1，因为层级从0开始。
    private Integer deepestLevel;
    // 在子树的树根节点所在层级之下的所有节点
    private Map<Integer, List<T>> allNode;
}
```

```java
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
```

这个方法是一个**初始化**方法：

1. 先获取子树树根节点所在层级之下的所有节点`this.allNode`，**按层级分组**。

> 如果需要一个节点就连接一次数据库的话，那数据库吃不消，所以一开始先查询出来所有**需要的节点**，储存到程序内存中，在程序中进行处理。不过如果节点数目很大的话，这种做法也不可取，一方面查数据库会慢，另一方面内存吃不消，所以优化一下可以分片段的查询数据库。

2. `rootThePath`即红色节点，**代表子树的根节点**。
3. `deepestLevel`即粉色箭头，即**把该层级当作叶子节点层**。

<img src="https://lee-img-bed.oss-cn-shenzhen.aliyuncs.com/tree/getSubTree-2.JPG" style="zoom:50%;" />

```java
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
                // 这里开始递归
                getChildren(payloadNode);
                childrenList.add(payloadNode);
            }
        }
        rootOfTheTree.setChildren(childrenList);
    }
```

这里可能会有疑问，为什么这个方法跟常规的递归算法不同，没有返回值呢，原因是入参的节点参数是一个对象变量，传进来的其实是一个指针指向其堆内存空间地址，所以我们在这个方法作用域（栈）里对指针指向的内容进行修改，是直接改变了堆内存空间里存储的内容，故不需要返回值。

```java
    /**
     * init初始化根节点 root 后，执行此方法，生成root的子树
     * @return
     */
    public PayloadNode<T> getSubTree() {
        getChildren(this.root);
        return this.root;
    }

```

这里就是简单的调用一下`getChildren`递归方法就可以实现构造子树了。

## increaseSubCountOfThePath

这真的是个很常用的功能，例如一棵类目树，给一个节点A新增了一个孩子节点，那么节点A的所有祖先节点的子孙节点数量`subCount`字段都应该+1，这个方法就是实现这个功能。

```java
/**
     * 把根据给定路径头检索得到的路径上的所有节点（不包括路径头）的子节点数量添上增量。
     *
     * @param headOfThePath 路径头，即最深的子节点
     * @param increment     subCount增量
     * @param saveOrNot     是否需要保存到数据库
     * @return 添上增量后的路径
     */
    List<T> increaseSubCountOfThePath(T headOfThePath, int increment, IService<T> service, boolean saveOrNot);
```

```java
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
```

<img src="https://lee-img-bed.oss-cn-shenzhen.aliyuncs.com/tree/getPathToRootFrom.JPG" style="zoom:50%;" />

这里就是调用`getPathToRootFrom`方法取得一条从子节点到根节点的路径，把路径上的节点`subCount`字段都加上增量就可以了。



## 测试一下

假设现在有一个权限系统中的**页面资源**

### DDL

```mysql
/**
  页面资源表
 */
create table webpage_resource
(
    id          bigint auto_increment primary key comment '主键，自增',
    par_id      bigint not null comment '上级id，0->一级菜单',
    name        varchar(64) not null COMMENT '资源名',
    text        varchar(64) comment '页面文字内容',
    path         varchar(64) COMMENT 'URL',
    type        int not null comment '类型，0->菜单，1->菜单项，2->超链接，3->按钮',
    description varchar(200) comment '资源描述',
    level       int comment '层级',
    sub_count    int default 0 comment '拥有子资源',
    create_by   bigint not null comment '创建页面资源的账户id，0->系统初始化',
    update_by   bigint not null comment '修改该页面资源的账号id，0->系统初始化',

    create_time datetime default current_timestamp comment '创建时间',
    update_time datetime default current_timestamp on update current_timestamp comment '最后一次更新时间'
) comment '网页资源表';
```

### DML

```mssql
# 初始化页面资源
insert into webpage_resource(par_id, name, text, path, type, description, level, create_by, update_by, sub_count)
VALUES (0, 'frontend-resource', '前台资源', '', 0, '前台微信小程序的顶级资源', 0, 0 ,0, 0),
       (0, 'admin-resource', '后台管理系统资源', '', 0, '后台管理系统的顶级资源', 0, 0 ,0, 6),
       (2, 'ams', '权限管理系统', '', 0, '权限管理系统二级资源，其下有角色管理与资源管理', 1, 0, 0, 3),
       (2, 'pms', '商品管理系统', '', 0, '商品管理系统二级资源', 1, 0, 0, 1),
       (3, 'role', '角色管理', '', 0, '角色管理，角色列表和添加角色等', 2, 0, 0, 2),
       (5, 'role-list', '角色列表', '', 0, '角色列表页', 3, 0, 0, 0),
       (5, 'role-add', '添加角色', '', 0, '添加角色', 3, 0, 0, 0),
       (4, 'category', '类目管理', '', 0, '类目管理，类目列表和添加类目等', 2, 0, 0, 0);
```

![](https://lee-img-bed.oss-cn-shenzhen.aliyuncs.com/tree/db-screenshot.JPG)

### 实体类

```java
/**
 * <p>
 * 网页资源表
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WebpageResource extends BaseEntity implements Serializable, TreeNode {

    private static final long serialVersionUID = 1L;

    /**
     * 上级id，0->一级菜单
     */
    private Long parId;

    /**
     * 资源名
     */
    private String name;

    /**
     * 页面文字内容
     */
    private String text;

    /**
     * URL
     */
    private String path;

    /**
     * 类型，0->菜单，1->菜单项，2->超链接，3->按钮
     */
    private Integer type;

    /**
     * 资源描述
     */
    private String description;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 拥有子资源
     */
    private Integer subCount;

    /**
     * 创建页面资源的账户id，0->系统初始化
     */
    private Long createBy;

    /**
     * 修改该页面资源的账号id，0->系统初始化
     */
    private Long updateBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后一次更新时间
     */
    private LocalDateTime updateTime;

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }

}
```

### 测试用例

```java
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApplicationTest {

    @Autowired
    IWebpageResourceService webpageResourceService;
    @Autowired
    ITreeService<WebpageResource> treeService;

    @Test
    public void testGetSubTree() {
        WebpageResource webpageResource = webpageResourceService.getByName("admin-resource");
        PayloadNode<WebpageResource> payloadNode = treeService.getSubTreeOf(webpageResource, webpageResourceService);
        String jsonStr = JSONUtil.toJsonPrettyStr(payloadNode);
        log.info(jsonStr);
    }
}

```

![](https://lee-img-bed.oss-cn-shenzhen.aliyuncs.com/tree/getSubTree-3.JPG)

```json
{
    "payload": {
        "parId": 0,
        "level": 0,
        "description": "后台管理系统的顶级资源",
        "updateTime": 1584345286000,
        "type": 0,
        "subCount": 0,
        "path": "",
        "createBy": 0,
        "updateBy": 0,
        "createTime": 1584345286000,
        "name": "admin-resource",
        "text": "后台管理系统资源",
        "id": 2
    },
    "children": [
        {
            "payload": {
                "parId": 2,
                "level": 1,
                "description": "权限管理系统二级资源，其下有角色管理与资源管理",
                "updateTime": 1584345286000,
                "type": 0,
                "subCount": 0,
                "path": "",
                "createBy": 0,
                "updateBy": 0,
                "createTime": 1584345286000,
                "name": "ams",
                "text": "权限管理系统",
                "id": 3
            },
            "children": [
                {
                    "payload": {
                        "parId": 3,
                        "level": 2,
                        "description": "角色管理，角色列表和添加角色等",
                        "updateTime": 1584345286000,
                        "type": 0,
                        "subCount": 0,
                        "path": "",
                        "createBy": 0,
                        "updateBy": 0,
                        "createTime": 1584345286000,
                        "name": "role",
                        "text": "角色管理",
                        "id": 5
                    },
                    "children": [
                        {
                            "payload": {
                                "parId": 5,
                                "level": 3,
                                "description": "角色列表页",
                                "updateTime": 1584345286000,
                                "type": 0,
                                "subCount": 0,
                                "path": "",
                                "createBy": 0,
                                "updateBy": 0,
                                "createTime": 1584345286000,
                                "name": "role-list",
                                "text": "角色列表",
                                "id": 6
                            },
                            "children": [
                            ]
                        },
                        {
                            "payload": {
                                "parId": 5,
                                "level": 3,
                                "description": "添加角色",
                                "updateTime": 1584345286000,
                                "type": 0,
                                "subCount": 0,
                                "path": "",
                                "createBy": 0,
                                "updateBy": 0,
                                "createTime": 1584345286000,
                                "name": "role-add",
                                "text": "添加角色",
                                "id": 7
                            },
                            "children": [
                            ]
                        }
                    ]
                }
            ]
        },
        {
            "payload": {
                "parId": 2,
                "level": 1,
                "description": "商品管理系统二级资源",
                "updateTime": 1584345286000,
                "type": 0,
                "subCount": 0,
                "path": "",
                "createBy": 0,
                "updateBy": 0,
                "createTime": 1584345286000,
                "name": "pms",
                "text": "商品管理系统",
                "id": 4
            },
            "children": [
                {
                    "payload": {
                        "parId": 4,
                        "level": 2,
                        "description": "类目管理，类目列表和添加类目等",
                        "updateTime": 1584345286000,
                        "type": 0,
                        "subCount": 0,
                        "path": "",
                        "createBy": 0,
                        "updateBy": 0,
                        "createTime": 1584345286000,
                        "name": "category",
                        "text": "类目管理",
                        "id": 8
                    },
                    "children": [
                    ]
                }
            ]
        }
    ]
}
```

没问题，嘤嘤嘤。

## Javascript树操作
这几天写前端业务，遇到需要从`PayloadNode`的`Payload`中提取字段的问题。
前端用到的`IView`的级联选择器组件，它规定了数据的结构，必须用`label`字段存储名称，`value`字段存储标识。
即后端返回的数据结构是这样的：
```javascript
[
  {
    payload: {
      id: 1,
      text: '店主'
    },
    children: [
      {
        id: 3,
        text: '商品管理员'
      }
    ]
  },
  {
    payload: {
      id: 2,
      text: '超级管理员'
    },
    children: []
  }
]
```
而前端需要的结构是这样的：
```javascript
[
  {
    value: 1,
    label: '店主'
    payload: {
      id: 1,
      text: '店主'
    },
    children: [
      {
        value: 3,
        label: '商品管理员'
        payload: {
          id: 3,
        text: '商品管理员'
        },
        children: []
      }
    ]
  },
  {
    value: 2,
    label: '超级管理员'
    payload: {
      id: 2,
      text: '超级管理员'
    },
    children: []
  }
]
```
但是呢，IView的树形控件有要求`title`是名称，所以写了一个方法，可以把`payload`中的树形映射到外层json并取一个别名。
```javascript
/**
 * 从payload中提取属性到外层。
 * @param {*} payloadNodeArr payloadNode列表
 * @param {*} mapArr  映射关系，一个二维数组，即要提取的属性在外层json的别名。
 */
export const peekAttributeFromPayloadToOutside = (payloadNodeArr, mapArr) => {
  const recursion = (list) => {
    list.forEach(node => {
      mapArr.forEach(map => {
        node[map[0]] = node.payload[map[1]];
      })
      if(node.children instanceof Array && node.children.length > 0) {
        recursion(node.children);
      }
    })
  }
  recursion(payloadNodeArr);
}
```



