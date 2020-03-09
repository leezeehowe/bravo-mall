package per.lee.bravo.mall.usercenter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import per.lee.bravo.mall.usercenter.constant.entityStatus.FundamentalAccountInfoCompletingStatusEnum;
import per.lee.bravo.mall.usercenter.constant.entityStatus.UserGenderEnum;
import per.lee.bravo.mall.usercenter.entity.FundamentalAccountEntity;
import per.lee.bravo.mall.usercenter.service.FundamentalAccountEntityService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PostUserInfoFromWechatTest {

    @Autowired
    FundamentalAccountEntityService fundamentalAccountEntityService;

    @Test
    public void testPoseUserInfoFromWechat() {
        FundamentalAccountEntity fundamentalAccountEntity = fundamentalAccountEntityService.getById(1);
        UserGenderEnum genderEnum = fundamentalAccountEntity.getGender();
        FundamentalAccountInfoCompletingStatusEnum accountInfoCompletingStatusEnum = fundamentalAccountEntity.getIsInfoCompleted();
        return;
    }

}
