package per.lee.bravo.mall.commodity.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import per.lee.bravo.mall.commodity.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import per.lee.bravo.mall.commodity.restful.protocol.BravoApiException;

/**
 * <p>
 * 类目表 服务类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-08
 */
public interface ICategoryService extends IService<Category> {

    /**
     * 根据分类名查找类目
     * @param name
     * @return
     */
    Category getOneByName(String name);

    Category createOne(Category dto) throws BravoApiException;
}
