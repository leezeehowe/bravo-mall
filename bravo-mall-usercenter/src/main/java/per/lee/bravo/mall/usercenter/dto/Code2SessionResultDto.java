package per.lee.bravo.mall.usercenter.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Code2SessionResultDto {
    private String openid;
    private String session_key;
    private String unionid;
    private Integer errcode;
    private String errmsg;

    private boolean success;

    public boolean isSuccess() {
        return this.errcode == null || this.errcode == 0;
    }
}
