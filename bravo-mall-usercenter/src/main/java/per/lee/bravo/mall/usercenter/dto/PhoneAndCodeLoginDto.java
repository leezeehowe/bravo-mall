package per.lee.bravo.mall.usercenter.dto;

import lombok.Data;

@Data
public class PhoneAndCodeLoginDto {

    private String phoneNumber;

    private String smsCode;

}
