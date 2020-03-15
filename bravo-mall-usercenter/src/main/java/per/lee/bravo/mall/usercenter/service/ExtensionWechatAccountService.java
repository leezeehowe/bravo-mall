package per.lee.bravo.mall.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import per.lee.bravo.mall.usercenter.entity.ExtensionWechatAccount;

public interface ExtensionWechatAccountService extends IService<ExtensionWechatAccount> {

    ExtensionWechatAccount getByOpenId(String openId);

}
