package per.lee.bravo.mall.usercenter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import per.lee.bravo.mall.usercenter.entity.FundamentalAccountEntity;
import per.lee.bravo.mall.usercenter.mapper.FundamentalAccountEntityMapper;
import per.lee.bravo.mall.usercenter.service.FundamentalAccountEntityService;


@Service
public class FundamentalAccountEntityServiceImpl extends ServiceImpl<FundamentalAccountEntityMapper, FundamentalAccountEntity> implements FundamentalAccountEntityService {

    @Override
    public FundamentalAccountEntity getByUUID(String uuid) {
        QueryWrapper<FundamentalAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FundamentalAccountEntity.UUID_COLUMN_NAME, uuid);
        return getOne(queryWrapper);
    }

    @Override
    public boolean saveOrUpdate(FundamentalAccountEntity entity) {
        String uuid = entity.getUuid();
        FundamentalAccountEntity original = getByUUID(uuid);
        if(original != null) {
            entity.setId(original.getId());
        }
        return super.saveOrUpdate(entity);
    }
}
