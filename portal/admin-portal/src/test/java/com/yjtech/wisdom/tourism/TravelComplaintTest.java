package com.yjtech.wisdom.tourism;

import com.yjtech.wisdom.tourism.command.service.travelcomplaint.TravelComplaintService;
import com.yjtech.wisdom.tourism.common.sms.MessageCallDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 旅游投诉 测试
 *
 * @date 2021/8/20 14:27
 * @author horadirm
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminPortal.class)
public class TravelComplaintTest {

    @Autowired
    private TravelComplaintService travelComplaintService;


    /**
     * 通过事件id，查询事件信息
     *
     * @date 2021/8/20 14:28
     * @author horadirm
     */
    @Test
    public void queryEvent(){
        Long[] ids = {};

        List<MessageCallDto> dtoList = travelComplaintService.queryEvent(ids);

        System.out.println(dtoList);
    }



}
