package per.lee.bravo.mall.usercenter.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenVo {

    private String accessToken;

    private String refreshToken;
}
