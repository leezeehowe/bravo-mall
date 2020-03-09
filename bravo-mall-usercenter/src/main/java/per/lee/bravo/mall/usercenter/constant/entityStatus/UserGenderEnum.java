package per.lee.bravo.mall.usercenter.constant.entityStatus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.Setter;

public enum UserGenderEnum {

    UNKNOWN(0, "未知"),
    MALE(1, "男性"),
    FEMALE(2, "女性");

    @Getter @Setter @EnumValue
    private Integer code;
    @Getter @Setter
    private String des;

    UserGenderEnum(int i) {
        setCode(i);
    }

    UserGenderEnum(int i, String des) {
        setCode(i);
        setDes(des);
    }

    public static UserGenderEnum valueOf(int code) {
        for (UserGenderEnum value : UserGenderEnum.values()) {
            if(value.code == code) {
                return value;
            }
        }
        return null;
    }

}
