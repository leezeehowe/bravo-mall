package per.lee.bravo.mall.authorization;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import per.lee.bravo.mall.authorization.entity.WebpageResource;
import per.lee.bravo.mall.authorization.tree.po.PayloadNode;
import per.lee.bravo.mall.authorization.tree.service.ITreeService;
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

    @Test
    public void test3() {
        treeService.getAllNodeBetweenSpecifiedLevel(0, 5, webpageResourceService);
    }

    @Test
    public void testGetSubTree() {
        WebpageResource webpageResource = webpageResourceService.getByName("admin-resource");
        PayloadNode<WebpageResource> payloadNode = treeService.getSubTreeOf(webpageResource, webpageResourceService);
        String jsonStr = JSONUtil.toJsonPrettyStr(payloadNode);
        log.info(jsonStr);
    }
}
