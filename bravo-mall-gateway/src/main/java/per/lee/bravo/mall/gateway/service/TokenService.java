package per.lee.bravo.mall.gateway.service;


import io.jsonwebtoken.*;

public interface TokenService {

    /**
     * 颁发accessToken
     * @param audience 所属用户的UUID
     * @param subject 所属客户端
     * @return accessToken
     */
    String issueJwtAccessToken(String audience, String subject);

    /**
     * 校验accessToken（格式、是否过期、是否被篡改等等..）
     * @param token accessToken
     * @param requiredAudience 指定该token的payload中的audience的值，不相等则抛出异常。
     * @return 校验通过的话，返回payload
     */
    Jws<Claims> verifyAccessToken(String token, String requiredAudience);

    /**
     * 从Http请求的Authorization字段中提取出token字符串
     * @param headerVal Authorization字段的字符串
     * @param tokenName token的名称，如 “Bearer”
     * @return token的值
     */
    String extractTokenValueFromHttpHeader(String headerVal, String tokenName);
}
