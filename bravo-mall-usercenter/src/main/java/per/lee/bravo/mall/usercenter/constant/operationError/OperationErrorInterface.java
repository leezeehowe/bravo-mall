package per.lee.bravo.mall.usercenter.constant.operationError;

import per.lee.bravo.mall.usercenter.constant.ExternalPlatformIdentityEnum;

public interface OperationErrorInterface {

    /**
     * 获取平台标识
     * @return 平台标识，
     */
    ExternalPlatformIdentityEnum getPlatformIdentity();

    String getCode();

    String getDes();
}
