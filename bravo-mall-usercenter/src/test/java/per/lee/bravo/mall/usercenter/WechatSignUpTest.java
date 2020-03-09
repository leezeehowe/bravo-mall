package per.lee.bravo.mall.usercenter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import per.lee.bravo.mall.usercenter.controller.LogInController;
import per.lee.bravo.mall.usercenter.exception.base.OperationException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WechatSignUpTest {

    @Autowired
    private LogInController logInController;


    /**
     * 测试微信注册
     * @throws OperationException
     */
    @Test
    public void testSignUpByWechatWithCode() throws OperationException {
        String code;
        String token;
        code = "023zr5ab0N9ypB19mO8b0IC6ab0zr5aN";
        token = logInController.tokenForWechatMiniProgram(code).get("accessToken");
        return;
    }

}
