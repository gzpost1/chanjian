import com.yjtech.wisdom.tourism.AppPortal;
import com.yjtech.wisdom.tourism.mybatis.typehandler.EncryptTypeHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppPortal.class)
public class TestEncrypt {

    @Test
    public void encrypt(){
        String encrypt = EncryptTypeHandler.AES.encrypt("123456");
        System.out.println(encrypt);
    }
}
