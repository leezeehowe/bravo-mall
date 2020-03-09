package per.lee.bravo.mall.gateway.service.impl;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import per.lee.bravo.mall.gateway.service.TokenService;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    // token密钥明文
    @Value("${token.secret}")
    private String tokenSecret;
    // token有效期, 单位/日
    @Value("${token.expireDate}")
    private int expireDate;
    // token发行方
    @Value("${token.issuer}")
    private String issuer;


    @Override
    public String issueJwtAccessToken(String audience, String subject) {
        Calendar calendar = Calendar.getInstance();
        // token密钥密文
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(tokenSecret));
        calendar.add(Calendar.DATE, expireDate);
        return Jwts.builder()
                .setAudience(audience)
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(calendar.getTime())
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Jws<Claims> verifyAccessToken(String token, String requiredAudience) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(tokenSecret));
        JwtParserBuilder builder = Jwts.parserBuilder().setSigningKey(secretKey);
        if(!StrUtil.isEmptyOrUndefined(requiredAudience)) {
            builder.requireAudience(requiredAudience);
        }
        return builder.build().parseClaimsJws(token);
    }

    @Override
    public String extractTokenValueFromHttpHeader(String headerVal, String tokenName) {
        if(StrUtil.isEmptyOrUndefined(headerVal) || StrUtil.isEmptyOrUndefined(tokenName) || headerVal.length() <= tokenName.length()) {
            return null;
        }
        else {
            int index = StrUtil.indexOf(headerVal, tokenName,0 , false);
            return StrUtil.sub(headerVal, index + tokenName.length(), headerVal.length()).trim();
        }
    }

}
