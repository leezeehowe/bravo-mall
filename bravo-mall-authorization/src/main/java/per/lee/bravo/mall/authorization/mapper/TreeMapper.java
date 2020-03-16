package per.lee.bravo.mall.authorization.mapper;

import org.springframework.stereotype.Repository;

@Repository
public interface TreeMapper {

    /**
     * 获取树的深度
     * @param tableName 表名
     * @return 树的深度-1
     */
    Integer selectDeepestLevel(String tableName);

}
