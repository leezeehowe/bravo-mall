package per.lee.bravo.mall.usercenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.lee.bravo.mall.usercenter.dto.PhoneAndCodeLoginDto;
import per.lee.bravo.mall.usercenter.restful.protocol.BravoApiException;
import per.lee.bravo.mall.usercenter.service.UserService;
import per.lee.bravo.mall.usercenter.vo.TokenVo;

import java.util.Optional;

@RestController
@RequestMapping("/${api.version}/token")
public class LogInController {

    @Autowired
    private UserService userService;

    /**
     * 微信小程序登录：
     * 根据用户传来的来自微信的临时登录凭证，向微信获取该用户的账号信息，若该用户是初次登录则执行注册流程然后再执行登录流程返回token
     * @return token
     */
    @GetMapping("/wechat")
    public TokenVo wechatMiniProgramLogin(String code) throws BravoApiException {
        // 参数校验
        code = Optional.ofNullable(code).orElseThrow(() -> new IllegalArgumentException("临时登录凭证-code不能为空！")).trim();
        return new TokenVo(userService.signInByWechatWithCode(code), null);
    }

    /**
     * 平台登录：
     * 根据手机号和验证码登录
     * @param dto 登录参数
     * @return token
     */
    @PostMapping("/platform")
    public TokenVo platformLoginIn(@RequestBody PhoneAndCodeLoginDto dto) throws BravoApiException {
        String token = userService.signIn(dto.getPhoneNumber(), dto.getSmsCode());
        return new TokenVo(token, null);
    }

}
