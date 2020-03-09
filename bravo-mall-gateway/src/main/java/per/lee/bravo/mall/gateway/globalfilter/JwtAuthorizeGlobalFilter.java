package per.lee.bravo.mall.gateway.globalfilter;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import per.lee.bravo.mall.gateway.bean.SkipTokenInspectionUrlBean;
import per.lee.bravo.mall.gateway.service.TokenService;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器，校验bravo-mall-usercenter所颁发的accessToken
 */
@Component
@Slf4j
public class JwtAuthorizeGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private SkipTokenInspectionUrlBean skipTokenInspectionUrlBean;
    @Value("${token.uuidKey}")
    private String UUID_KEY;
    @Value("${token.tokenName}")
    private String TOKEN_NAME;

    private static final String TOKEN_KEY = "Authorization";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = request.getHeaders();
        String token = StrUtil.cleanBlank(headers.getFirst(TOKEN_KEY));
        String uuid = StrUtil.cleanBlank( headers.getFirst(UUID_KEY));

        if(skipTokenInspectionUrlBean.skipTokenInspection(request)) {
            log.info("跳过token验证：请求路径={} 请求方法={} ", request.getPath().toString(), request.getMethod() != null ? request.getMethod().name() : "");
            return chain.filter(exchange);
        }

        // 从header的Authorization字段中提取token的实际值，因为是bearer开头的
        if(!StrUtil.isEmptyOrUndefined(token)) {
            token = tokenService.extractTokenValueFromHttpHeader(token, TOKEN_NAME);
        }

        // 无token，不管有无uuid都不合法, 返回401
        if(StrUtil.isEmptyOrUndefined(token)) {
            log.info("无携带token，拦截请求：请求路径={} 请求方法={} ", request.getPath().toString(), request.getMethod() != null ? request.getMethod().name() : "");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        try {
            // 有token， 无uuid
            if(!StrUtil.isEmptyOrUndefined(token) && StrUtil.isEmptyOrUndefined(uuid)) {
                Jws<Claims> claims = tokenService.verifyAccessToken(token, null);
                uuid = claims.getBody().getAudience();
                String finalUuid = uuid;
                request.mutate().headers((httpHeaders) -> {
                    httpHeaders.set(UUID_KEY, finalUuid);
                }).build();
            }

            // 有token， 有uuid
            if(!StrUtil.isEmptyOrUndefined(token) && !StrUtil.isEmptyOrUndefined(uuid)) {
                // 校验uuid是否与token的一致
                tokenService.verifyAccessToken(token, uuid);
            }

            log.info("用户请求\ntoken: {}\n uuid: {}", token, uuid);
            return chain.filter(exchange);
        }
        catch (ExpiredJwtException e) {
            String message = "token已过期," + e.getMessage();
            log.info(message);
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException | SignatureException e) {
            String message = "不合法的token," + e.getMessage();
            log.info(message);
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

