package com.yjtech.wisdom.tourism.portal.controller.chat;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.chat.dto.Message;
import com.yjtech.wisdom.tourism.chat.dto.MessageRecordQuery;
import com.yjtech.wisdom.tourism.chat.service.ChatMessageService;
import com.yjtech.wisdom.tourism.chat.service.ChatRecordService;
import com.yjtech.wisdom.tourism.chat.vo.ChatMessageVo;
import com.yjtech.wisdom.tourism.chat.vo.EnterpriseVo;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  大屏 -留言
 * @author han
 * @createTime 2022/5/12 17:30
 * @description
 */
@RestController
@RequestMapping("chat")
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatRecordService chatRecordService;


    /**
     * 查询两人的聊天记录
     *
     * @param messageRecordQuery
     * @return
     */
    @RequestMapping("/getMessageHistory")
    public JsonResult<List<ChatMessageVo>> getMessageHistory(@RequestBody MessageRecordQuery messageRecordQuery) {
        if (messageRecordQuery.getDataLimit() == null) {
            throw new CustomException("00001", "请设置查询数据相关限制");
        }
        List<ChatMessageVo> chatMessageVoList = chatMessageService.getMessageHistory(messageRecordQuery);
        return JsonResult.success(chatMessageVoList);
    }

    /**
     * 获取与当前企业聊天的企业
     */
    @RequestMapping("/queryChatObject")
    public JsonResult<Page<EnterpriseVo>> queryChatObject(@RequestBody MessageRecordQuery messageRecordQuery) {
        Page<EnterpriseVo> enterpriseVoPage = chatRecordService.queryChatObject(messageRecordQuery);
        return JsonResult.success(enterpriseVoPage);
    }

    /**
     * 获取与当前企业聊天的企业
     */
    @GetMapping("/queryChatObject")
    public JsonResult<List<EnterpriseVo>> queryChatObject(@RequestParam("initiatorId") Long initiatorId) {
        List<EnterpriseVo> enterpriseVoList = chatRecordService.queryChatObject(initiatorId);
        return JsonResult.success(enterpriseVoList);
    }

    /**
     * 删除聊天记录
     *
     * @param id
     * @return
     */
    @DeleteMapping("/deleteRecord")
    public JsonResult<Boolean> deleteRecord(@RequestParam("id") Long id) {
        Boolean result = chatRecordService.deleteRecord(id);
        return JsonResult.success(result);
    }

    /**
     * 将消息置为已读
     *
     * @param enterpriseVo
     * @return
     */
    @RequestMapping("/readMesg")
    public JsonResult<Boolean> readMesg(@RequestBody EnterpriseVo enterpriseVo) {
        chatMessageService.readMesg(enterpriseVo.getId());
        return JsonResult.success();
    }

    /**
     * 发送消息
     * @param message
     * @return
     */
    @RequestMapping("/sendMessage")
    public JsonResult<Boolean> sendMessage(@RequestBody Message message){
        Boolean result = chatMessageService.sendMessage(message);
        return JsonResult.success(result);
    }
}
