package com.yjtech.wisdom.tourism.portal.controller.travelcomplaint;

import com.yjtech.wisdom.tourism.command.entity.travelcomplaint.TravelComplaintEntity;
import com.yjtech.wisdom.tourism.command.service.travelcomplaint.TravelComplaintService;
import com.yjtech.wisdom.tourism.command.vo.TravelComplaintCreateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 旅游投诉 大屏
 *
 * @Author horadirm
 * @Date 2021/7/21 9:38
 */
@RestController
@RequestMapping("/travelComplaint/screen/")
public class TravelComplaintScreenController {

    @Autowired
    private TravelComplaintService travelComplaintService;


}
