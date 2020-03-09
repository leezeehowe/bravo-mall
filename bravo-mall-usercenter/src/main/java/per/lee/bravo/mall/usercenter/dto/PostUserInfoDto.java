package per.lee.bravo.mall.usercenter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import per.lee.bravo.mall.usercenter.constant.entityStatus.UserGenderEnum;

@Data
@AllArgsConstructor
public class PostUserInfoDto {
    private String nickName;
    private String avatarUrl;
    private UserGenderEnum gender;
    private String country;
    private String province;
    private String city;
}
