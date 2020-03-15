package per.lee.bravo.mall.usercenter;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import per.lee.bravo.mall.usercenter.controller.LogInController;
import per.lee.bravo.mall.usercenter.redis.RedisService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WechatSignUpTest {

    @Autowired
    private LogInController logInController;
    @Autowired
    private RedisService redisService;


}
