import com.yjtech.wisdom.tourism.AppPortal;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.mapper.TbRegisterInfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppPortal.class)
public class testMyBatis {
    @Autowired
    TbRegisterInfoMapper tbRegisterInfoMapper;
    @Test
    public void testMyBatisXml(){
    }
}
