package per.lee.bravo.mall.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import per.lee.bravo.mall.usercenter.entity.ExtensionWechatAccount;
import per.lee.bravo.mall.usercenter.entity.FundamentalAccount;

import java.util.List;

public interface ExtensionWechatAccountService extends IService<ExtensionWechatAccount> {


    ExtensionWechatAccount getByOpenId(String openId);

    List<ExtensionWechatAccount> list(List<FundamentalAccount> fundamentalAccountList);

}
