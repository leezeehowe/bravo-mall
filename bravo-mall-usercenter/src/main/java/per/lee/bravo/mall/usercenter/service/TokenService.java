package per.lee.bravo.mall.usercenter.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface TokenService {

    /**
     * 颁发accessToken
     * @param audience 所属用户的UUID
     * @param subject 所属客户端
     * @return accessToken
     */
    String issueJwtAccessToken(String audience, String subject);

    /**
     * 解析accessToken
     * @param accessToken 待校验的accessToken
     */
    Jws<Claims> decodeAccessToken(String accessToken);



}
