package per.lee.bravo.mall.usercenter.controller;

import cn.hutool.json.JSONObject;
import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.lee.bravo.mall.usercenter.exception.wechat.Code2SessionApiException;
import per.lee.bravo.mall.usercenter.service.UserService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/logIn")
public class LogInController {

    @Autowired
    private UserService userService;

    /**
     * 微信小程序注册：
     * 根据前台获取的code向微信获取token
     * @return token
     */
    @GetMapping("/wechat/mini/token")
    public Map<String, String> tokenForWechatMiniProgram(String code) throws Code2SessionApiException {
        // 参数校验
        code = Optional.ofNullable(code).orElseThrow(() -> new IllegalArgumentException("临时登录凭证-code不能为空！")).trim();
        return Map.of("accessToken", userService.signUpByWechatWithCode(code));
    }

}
