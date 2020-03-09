package per.lee.bravo.mall.usercenter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import per.lee.bravo.mall.usercenter.service.TokenService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TokenTest {

    @Autowired
    private TokenService tokenService;

    @Test
    public void testIssueJwtAccessToken() {
        String token = tokenService.issueJwtAccessToken("user's uuid", "bravo-mall");
        log.info("the issued token: {}", token);
    }

}
