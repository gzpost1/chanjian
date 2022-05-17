package com.yjtech.wisdom.tourism.chat.conver;

import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.chat.entity.ChatMessageEntity;
import com.yjtech.wisdom.tourism.chat.vo.ChatMessageVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author han
 * @createTime 2022/5/13 15:03
 * @description
 */
public class ChatMessageCover {

    public static ChatMessageCover INSTANCE = new ChatMessageCover();

    public List<ChatMessageVo> cover2ChatMessageVo(List<ChatMessageEntity> chatMessageVoList, Map<Long, TbRegisterInfoEntity> infoEntityMap) {
        if (chatMessageVoList != null) {
            List<ChatMessageVo> chatMessageVos =new ArrayList<>();
            ChatMessageVo chatMessageVo;
            for (ChatMessageEntity entity : chatMessageVoList) {
                chatMessageVo =new ChatMessageVo();
                chatMessageVo.setFromUserId(entity.getFromUserId());
                chatMessageVo.setFromName(infoEntityMap.get(entity.getFromUserId()).getCompanyName());
                chatMessageVo.setToUserId(entity.getToUserId());
                chatMessageVo.setToName(infoEntityMap.get(entity.getToUserId()).getCompanyName());
                chatMessageVo.setContent(entity.getContent());
                chatMessageVo.setSendTime(entity.getSendTime());
                chatMessageVos.add(chatMessageVo);
            }
            return chatMessageVos;
        }
        return null;
    }
}
