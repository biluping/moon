import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.moon.MoonServerApplication;
import org.moon.controller.MoonAppController;
import org.moon.entity.ao.MoonAppAo;
import org.moon.exception.MoonBadRequestException;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = MoonServerApplication.class)
public class MoonAppTest {

    @Resource
    private MoonAppController moonAppController;

    @Test
    public void testCreateApp(){
        MoonAppAo ao = new MoonAppAo("example3", "http://127.0.0.1:8080");

        // 第一次创建成功
        moonAppController.create(ao);

        // 第二次创建失败,抛出异常
        Assertions.assertThrows(MoonBadRequestException.class, ()->moonAppController.create(ao));
    }

    @Test
    public void testGetApp(){
        List<MoonAppAo> apps = moonAppController.getApps();
        Assertions.assertTrue(apps.size()>0);
    }

}
