package per.lee.bravo.mall.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import per.lee.bravo.mall.usercenter.entity.FundamentalAccountEntity;

public interface FundamentalAccountEntityService extends IService<FundamentalAccountEntity> {

    FundamentalAccountEntity getByUUID(String uuid);

}
