package per.lee.bravo.mall.commodity.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.models.auth.In;
import per.lee.bravo.mall.commodity.constant.OperationErrorEnum;
import per.lee.bravo.mall.commodity.entity.Category;
import per.lee.bravo.mall.commodity.mapper.CategoryMapper;
import per.lee.bravo.mall.commodity.restful.protocol.BravoApiException;
import per.lee.bravo.mall.commodity.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Category getOneByName(String name) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return getOne(queryWrapper);
    }

    @Override
    public Category createOne(Category dto) throws BravoApiException {
        Category par = getById(dto.getParId());
        // 查重
        if(getOneByName(dto.getName()) != null) {
            throw new BravoApiException(OperationErrorEnum.CONFLICT_ENTITY_ERROR, StrUtil.format("已经存在[{}]该类目！请不要重复创建。", dto.getName()));
     }
        // 设置级别：如果是一级类目（parId=0）则设为0，否则为parLevel + 1；
        Integer parLevel = Optional.ofNullable(par).map((p) -> p.getLevel() + 1).orElse(0);
        dto.setLevel(parLevel);
        // 继承上级分类的数量单位，若无。
        if(StrUtil.isEmptyOrUndefined(dto.getProductUnit())) {
            String parProductUnit = Optional.ofNullable(par).map(Category::getProductUnit).orElseThrow(() ->new BravoApiException(OperationErrorEnum.CREATE_ENTITY_FAIL, "一级类目必须填写数量单位。"));
            dto.setProductUnit(parProductUnit);
        }
        try {
            this.save(dto);
            return getOneByName(dto.getName());
        }
        catch (Exception e) {
            throw new BravoApiException(OperationErrorEnum.CREATE_ENTITY_FAIL, e.getMessage());
        }
    }
}
