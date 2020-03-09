package per.lee.bravo.mall.usercenter.service.impl;

import cn.hutool.json.JSONObject;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import per.lee.bravo.mall.usercenter.service.TokenService;
import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

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
    public Jws<Claims> decodeAccessToken(String accessToken) {
        // token密钥密文
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(tokenSecret));
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken);
    }
}
