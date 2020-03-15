package per.lee.bravo.mall.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import per.lee.bravo.mall.usercenter.entity.FundamentalAccount;
import per.lee.bravo.mall.usercenter.mapper.FundamentalAccountEntityMapper;
import per.lee.bravo.mall.usercenter.service.FundamentalAccountService;


@Service
public class FundamentalAccountServiceImpl extends ServiceImpl<FundamentalAccountEntityMapper, FundamentalAccount> implements FundamentalAccountService {

    @Override
    public FundamentalAccount getByUUID(String uuid) {
        return getByColumn("uuid", uuid);

    }

    @Override
    public FundamentalAccount getByPhone(String phone) {
        return getByColumn("phone_number", phone);
    }

    private FundamentalAccount getByColumn(String name, String val) {
        QueryWrapper<FundamentalAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(name, val);
        return getOne(queryWrapper);
    }


    @Override
    public boolean saveOrUpdate(FundamentalAccount entity) {
        String uuid = entity.getUuid();
        FundamentalAccount original = getByUUID(uuid);
        if(original != null) {
            entity.setId(original.getId());
        }
        return super.saveOrUpdate(entity);
    }
}
