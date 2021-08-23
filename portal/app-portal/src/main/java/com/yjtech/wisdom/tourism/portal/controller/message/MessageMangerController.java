package com.yjtech.wisdom.tourism.portal.controller.message;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.command.service.travelcomplaint.TravelComplaintService;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.message.admin.dto.MessageDto;
import com.yjtech.wisdom.tourism.message.admin.dto.MessageRecordDto;
import com.yjtech.wisdom.tourism.message.admin.service.MessageMangerService;
import com.yjtech.wisdom.tourism.message.admin.vo.QueryMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 消息中心
 *
 * @author renguangqian
 * @date 2021/7/29 15:12
 */
@RestController
@RequestMapping("/api/message")
public class MessageMangerController {

    @Autowired
    private MessageMangerService messageMangerService;

    @Autowired
    private TravelComplaintService travelComplaintService;

    /**
     * 查询消息列表
     *
     * @param vo
     * @return
     */
    @PostMapping("queryPageMessage")
    public JsonResult<IPage<MessageDto>> queryPageMessage (@RequestBody @Validated QueryMessageVo vo) {
        return JsonResult.success(messageMangerService.queryPageMessage(vo, true,null, travelComplaintService));
    }

    /**
     * 查询当前用户新增消息
     *
     * @return
     */
    @GetMapping("queryNewMessageNum")
    public JsonResult<MessageRecordDto> queryNewMessageNum () {
        return JsonResult.success(messageMangerService.queryNewMessageNum(null, travelComplaintService));
    }
}
