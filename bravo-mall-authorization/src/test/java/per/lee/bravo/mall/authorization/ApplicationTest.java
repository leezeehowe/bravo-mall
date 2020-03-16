package per.lee.bravo.mall.authorization;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import per.lee.bravo.mall.authorization.entity.WebpageResource;
import per.lee.bravo.mall.authorization.tree.ITreeService;
import per.lee.bravo.mall.authorization.service.IWebpageResourceService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApplicationTest {

    @Autowired
    IWebpageResourceService webpageResourceService;
    @Autowired
    ITreeService<WebpageResource> treeService;

    @Test
    public void test() {
        WebpageResource webpageResource = webpageResourceService.getByName("role-add");
        List<WebpageResource> list = treeService.getPathToRootFrom(webpageResource, webpageResourceService);
        return;
    }

    @Test
    public void test2() {
        WebpageResource webpageResource = webpageResourceService.getByName("role-add");
        treeService.increaseSubCountOfThePath(webpageResource, 3, webpageResourceService, true);
    }

}
