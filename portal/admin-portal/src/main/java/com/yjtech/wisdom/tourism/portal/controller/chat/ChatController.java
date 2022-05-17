package com.yjtech.wisdom.tourism.portal.controller.chat;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.chat.dto.MessageRecordQuery;
import com.yjtech.wisdom.tourism.chat.service.ChatMessageService;
import com.yjtech.wisdom.tourism.chat.vo.ChatMessageVo;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.infrastructure.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author han
 * @createTime 2022/5/16 19:49
 * @description
 */
@ResponseBody
@RequestMapping("chat")
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    /**
     * 留言记录导出
     *
     * @param messageRecordQuery
     * @return
     */
    @RequestMapping("/exportMesg")
    public JsonResult exportMesg(@RequestBody MessageRecordQuery messageRecordQuery) {
        messageRecordQuery.setPageNo(1L);
        messageRecordQuery.setPageSize(999999L);
        Page<ChatMessageVo> chatMessageVoPage = chatMessageService.querySendMessage(messageRecordQuery);
        ExcelUtil<ChatMessageVo> util = new ExcelUtil<ChatMessageVo>(ChatMessageVo.class);
        return util.exportExcel(chatMessageVoPage.getRecords(), "留言记录");
    }

    /**
     * 查询发出的留言记录
     *
     * @param messageRecordQuery
     * @return
     */
    @RequestMapping("/querySendMessage")
    public JsonResult<Page<ChatMessageVo>> querySendMessage(@RequestBody MessageRecordQuery messageRecordQuery) {
        Page<ChatMessageVo> chatMessageVoList = chatMessageService.querySendMessage(messageRecordQuery);
        return JsonResult.success(chatMessageVoList);
    }

}
