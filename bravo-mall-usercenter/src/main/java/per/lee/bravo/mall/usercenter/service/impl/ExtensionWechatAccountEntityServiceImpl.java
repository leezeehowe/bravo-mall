package per.lee.bravo.mall.usercenter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import per.lee.bravo.mall.usercenter.entity.ExtensionWechatAccountEntity;
import per.lee.bravo.mall.usercenter.entity.FundamentalAccountEntity;
import per.lee.bravo.mall.usercenter.mapper.ExtensionWechatAccountEntityMapper;
import per.lee.bravo.mall.usercenter.service.ExtensionWechatAccountEntityService;

import java.util.Optional;

@Service
public class ExtensionWechatAccountEntityServiceImpl extends ServiceImpl<ExtensionWechatAccountEntityMapper, ExtensionWechatAccountEntity> implements ExtensionWechatAccountEntityService {

    @Override
    public ExtensionWechatAccountEntity getByOpenId(String openId) {
        QueryWrapper<ExtensionWechatAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ExtensionWechatAccountEntity.OPENID_COLUMN_NAME, openId);
        return getOne(queryWrapper);
    }



    @Override
    public boolean saveOrUpdate(ExtensionWechatAccountEntity entity) {
        String openId = entity.getOpenid();
        ExtensionWechatAccountEntity original = getByOpenId(openId);
        if(original != null) {
            Long id = original.getId();
            entity.setId(id);
        }
        return super.saveOrUpdate(entity);
    }
}
