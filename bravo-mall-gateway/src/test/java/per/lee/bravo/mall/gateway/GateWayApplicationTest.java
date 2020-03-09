package per.lee.bravo.mall.gateway;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import per.lee.bravo.mall.gateway.service.TokenService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GateWayApplicationTest {

    @Autowired
    private TokenService tokenService;

    @Test
    public void testExtractTokenValueFromHttpHeader() {
        String tokenName = "Bearer";
        String tokenVal = "123456";
        String headerVal = tokenName + " " + tokenVal;
        String token = tokenService.extractTokenValueFromHttpHeader(headerVal, "Bearer");
        Assert.assertEquals(tokenVal, token);
    }

}
