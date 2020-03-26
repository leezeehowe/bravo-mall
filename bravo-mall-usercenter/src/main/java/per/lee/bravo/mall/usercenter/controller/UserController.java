package per.lee.bravo.mall.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import per.lee.bravo.mall.usercenter.dto.PostUserInfoDto;
import per.lee.bravo.mall.usercenter.entity.FundamentalAccount;
import per.lee.bravo.mall.usercenter.restful.protocol.BravoApiException;
import per.lee.bravo.mall.usercenter.service.UserService;
import per.lee.bravo.mall.usercenter.vo.UserForAdminVo;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/${api.version}/user")
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

    @GetMapping("adminVo/page")
    public IPage<UserForAdminVo> pageAdminVo(Integer current, Integer size) {
        return userService.pageUserForAdminVo(current, size);
    }

    @GetMapping("adminVo/search")
    public UserForAdminVo searchUser(String username, String uuid, String phone) throws BravoApiException {
        return userService.search(phone, username, uuid);
    }

}
