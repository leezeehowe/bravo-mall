package per.lee.bravo.sms.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 短信的类型
 * @author lee
 */
@AllArgsConstructor
public enum SmsType {
    /**
     * 验证码类型
     */
    AUTH_CODE(1, "AUTH_CODE");

    @Getter
    @Setter
    private Integer code;

    @Getter
    @Setter
    private String des;

    SmsType(Integer code) {
        this.code = code;
    }
}
