import org.junit.jupiter.api.*;
import org.moon.MoonServerApplication;
import org.moon.controller.MoonConfigController;
import org.moon.entity.ao.ConfigAo;
import org.moon.entity.dto.AppConfigDto;
import org.moon.entity.vo.MoonConfigVo;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = MoonServerApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoonConfigTest extends BaseTest{

    @Resource
    private MoonConfigController controller;

    @Test
    @Order(1)
    public void testSaveConfig(){
        controller.saveConfig(appid, new ConfigAo("name", "张三"));
        controller.saveConfig(appid, new ConfigAo("name", "张三"));
        controller.saveConfig(appid, new ConfigAo("age", "1"));
        controller.saveConfig(appid, new ConfigAo("addr", "hangzhou"));
    }

    @Test
    @Order(2)
    public void testDelConfig(){
        controller.delKey(appid, "age");
    }

    @Test
    @Order(3)
    public void testPublishConfig(){
        controller.publish(appid, "addr");
    }

    @Test
    @Order(4)
    public void testPreviewConfig(){
        AppConfigDto dto = controller.getPreviewConfig(appid);
        Assertions.assertNotNull(dto.getCustomConfig());
        Assertions.assertNotNull(dto.getSystemConfig());
        Assertions.assertNotNull(dto.getSystemEnv());
    }

    @Test
    @Order(5)
    public void testGetConfig(){
        List<MoonConfigVo> configList = controller.getMoonConfig(appid, null);
        List<MoonConfigVo> noPublishConfig = controller.getMoonConfig(appid, 0);
        List<MoonConfigVo> publishConfig = controller.getMoonConfig(appid, 1);
        Assertions.assertNotNull(configList);
        Assertions.assertNotNull(noPublishConfig);
        Assertions.assertNotNull(publishConfig);

        Assertions.assertTrue(configList.size() > 0);
        Assertions.assertTrue(noPublishConfig.size() > 0);
        Assertions.assertTrue(publishConfig.size() > 0);
    }
}
