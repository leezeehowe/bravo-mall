package per.lee.bravo.mall.usercenter.constant.entityStatus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;
import lombok.Setter;

public enum FundamentalAccountInfoCompletingStatusEnum{

    COMPLETED(0, "已完善账号信息"),
    NOT_COMPLETED(1, "待完善账号信息");
    
    @Getter @Setter @EnumValue
    private Integer code;
    @Getter @Setter
    private String des;

    FundamentalAccountInfoCompletingStatusEnum(int code, String des) {
        this.code = code;
        this.des = des;
    }

    public static FundamentalAccountInfoCompletingStatusEnum valueOf(int code) {
        for (FundamentalAccountInfoCompletingStatusEnum value : FundamentalAccountInfoCompletingStatusEnum.values()) {
            if(value.code == code) {
                return value;
            }
        }
        return null;
    }
}
