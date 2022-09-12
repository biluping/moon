import com.alibaba.fastjson.TypeReference;
import org.junit.Test;
import org.moon.utils.HttpUtils;

import java.io.IOException;
import java.util.Map;

public class HttpTest {

    @Test
    public void testPost() throws IOException {
        Map<String, Object> map = HttpUtils.doPost(
                "https://155ef840-5b2f-4a68-a5db-39576858214d.mock.pstmn.io/config/custom/example",
                null,
                new TypeReference<>(){});
        System.out.println(map);
    }
}
