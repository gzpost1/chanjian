package com.yjtech.wisdom.tourism.portal.controller.message;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.message.admin.dto.MessageDto;
import com.yjtech.wisdom.tourism.message.admin.service.MessageMangerService;
import com.yjtech.wisdom.tourism.message.admin.vo.QueryMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息中心
 *
 * @author renguangqian
 * @date 2021/7/27 11:30
 */
@RestController
@RequestMapping("message")
public class MessageMangerController {

    @Autowired
    private MessageMangerService messageMangerService;

    @PostMapping("queryPageMessage")
    public JsonResult<IPage<MessageDto>> queryPageMessage(@RequestBody @Validated QueryMessageVo vo) {
        return JsonResult.success(messageMangerService.queryPageMessage(vo));
    }
}
