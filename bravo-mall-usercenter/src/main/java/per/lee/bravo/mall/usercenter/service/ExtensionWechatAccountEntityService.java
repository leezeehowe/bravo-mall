package per.lee.bravo.mall.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import per.lee.bravo.mall.usercenter.entity.ExtensionWechatAccountEntity;

public interface ExtensionWechatAccountEntityService extends IService<ExtensionWechatAccountEntity> {

    ExtensionWechatAccountEntity getByOpenId(String openId);

}
