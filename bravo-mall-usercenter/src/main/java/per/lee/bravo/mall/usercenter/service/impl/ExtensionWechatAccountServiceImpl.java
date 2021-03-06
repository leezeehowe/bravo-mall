package per.lee.bravo.mall.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import per.lee.bravo.mall.usercenter.entity.ExtensionWechatAccount;
import per.lee.bravo.mall.usercenter.entity.FundamentalAccount;
import per.lee.bravo.mall.usercenter.mapper.ExtensionWechatAccountEntityMapper;
import per.lee.bravo.mall.usercenter.service.ExtensionWechatAccountService;

import java.util.List;

@Service
public class ExtensionWechatAccountServiceImpl extends ServiceImpl<ExtensionWechatAccountEntityMapper, ExtensionWechatAccount> implements ExtensionWechatAccountService {

    @Override
    public ExtensionWechatAccount getByOpenId(String openId) {
        QueryWrapper<ExtensionWechatAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ExtensionWechatAccount.OPENID_COLUMN_NAME, openId);
        return getOne(queryWrapper);
    }

    @Override
    public List<ExtensionWechatAccount> list(List<FundamentalAccount> fundamentalAccountList) {
        QueryWrapper<ExtensionWechatAccount> queryWrapper = new QueryWrapper<>();
        fundamentalAccountList.forEach(fundamentalAccount -> {
           queryWrapper.eq("user_uuid", fundamentalAccount.getUuid()).or();
        });
        return this.list(queryWrapper);
    }


    @Override
    public boolean saveOrUpdate(ExtensionWechatAccount entity) {
        String openId = entity.getOpenid();
        ExtensionWechatAccount original = getByOpenId(openId);
        if(original != null) {
            Long id = original.getId();
            entity.setId(id);
        }
        return super.saveOrUpdate(entity);
    }
}
