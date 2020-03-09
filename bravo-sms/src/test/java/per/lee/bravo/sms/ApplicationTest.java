package per.lee.bravo.sms;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import per.lee.bravo.sms.bean.AlibabaSmsBean;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApplicationTest {

    @Autowired
    private AlibabaSmsBean alibabaSmsBean;

    @Test
    public void testSendSms() {
        try {
            throw new NullPointerException();
        }catch (Exception e) {
            log.info(e.getMessage());
        }
    }

}
