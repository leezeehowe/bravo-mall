package per.lee.bravo.mall.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import per.lee.bravo.mall.commodity.entity.Category;
import per.lee.bravo.mall.commodity.mapper.CategoryMapper;
import per.lee.bravo.mall.commodity.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 类目表 服务实现类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-08
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
