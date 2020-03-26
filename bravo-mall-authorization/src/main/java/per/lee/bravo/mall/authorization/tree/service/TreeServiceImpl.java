package per.lee.bravo.mall.authorization.tree.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.lee.bravo.mall.authorization.mapper.TreeMapper;
import per.lee.bravo.mall.authorization.tree.component.GetSubTreeComponent;
import per.lee.bravo.mall.authorization.tree.po.PayloadNode;
import per.lee.bravo.mall.authorization.tree.po.TreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TreeServiceImpl<T extends TreeNode> implements ITreeService<T> {

    @Autowired
    TreeMapper treeMapper;
    @Autowired
    GetSubTreeComponent<T> getSubTreeComponent;

    @Override
    public Integer getDeepestLevel(String tableName) {
        return treeMapper.selectDeepestLevel(tableName);
    }

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
        level = headOfThePath.getLevel();
        parId = headOfThePath.getParId();
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
    public PayloadNode<T>  getSubTreeOf(T rootOfTheTree, IService<T> service) {
        getSubTreeComponent.init(rootOfTheTree, this.getDeepestLevel("webpage_resource"), service);
        return getSubTreeComponent.getSubTree();
    }

    @Override
    public List<T> increaseSubCountOfThePath(T headOfThePath, int increment, IService<T> service, boolean saveOrNot) {
        List<T> path = this.getPathToRootFrom(headOfThePath, service);
        path = path.stream().peek((node) -> node.setSubCount(node.getSubCount() + increment)).collect(Collectors.toList());
        if(saveOrNot) {
            if(path.size() != 0) service.saveOrUpdateBatch(path, path.size());
        }
        return path;
    }

    @Override
    public List<T> getAllRootNode(IService<T> service) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("par_id", 0);
        return service.list(queryWrapper);
    }

}
