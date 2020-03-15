package per.lee.bravo.mall.usercenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import per.lee.bravo.mall.usercenter.dto.PostUserInfoDto;
import per.lee.bravo.mall.usercenter.entity.FundamentalAccount;
import per.lee.bravo.mall.usercenter.restful.protocol.BravoApiException;
import per.lee.bravo.mall.usercenter.service.UserService;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${token.uuidKey}")
    private String UUID_KEY;

    /**
     * 获取基本账户信息
     * @return 基本账户信息
     */
    @GetMapping
    public FundamentalAccount fundamentalAccountInfo(HttpServletRequest request) throws BravoApiException {
        String uuid = request.getHeader(UUID_KEY);
        return userService.getFundamentalAccountInfo(uuid);
    }

    /**
     * 补充或更新基本账户信息
     * @param dto 基本账户信息DTO
     * @return 是否成功
     */
    @PostMapping
    public boolean fundamentalAccountInfo(@RequestBody PostUserInfoDto dto, HttpServletRequest request) {
        String uuid = request.getHeader(UUID_KEY);
        return userService.saveOrUpdateAccountInfo(uuid, dto);
    }

}
