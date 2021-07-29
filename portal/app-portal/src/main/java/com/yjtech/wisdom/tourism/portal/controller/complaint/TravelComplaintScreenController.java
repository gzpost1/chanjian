package com.yjtech.wisdom.tourism.portal.controller.complaint;

import com.yjtech.wisdom.tourism.command.service.travelcomplaint.TravelComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 旅游投诉 大屏
 *
 * @Author horadirm
 * @Date 2021/7/27 16:29
 */
@RestController
@RequestMapping("/travelComplaint/screen/")
public class TravelComplaintScreenController {

    @Autowired
    private TravelComplaintService travelComplaintService;


}
