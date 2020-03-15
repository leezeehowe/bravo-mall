package per.lee.bravo.mall.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import per.lee.bravo.mall.usercenter.entity.FundamentalAccount;

public interface FundamentalAccountService extends IService<FundamentalAccount> {

    /**
     * 根据UUID获取基础账号信息
     * @param uuid UUID
     * @return
     */
    FundamentalAccount getByUUID(String uuid);

    /**
     * 根据手机号获取基础账号信息
     * @param phone 手机号
     * @return
     */
    FundamentalAccount getByPhone(String phone);

}
