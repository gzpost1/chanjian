package com.yjtech.wisdom.tourism.chat.conver;

import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.chat.entity.ChatRecordEntity;
import com.yjtech.wisdom.tourism.chat.vo.EnterpriseVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author han
 * @createTime 2022/5/13 10:32
 * @description
 */
public class ChatRecordCover {

    public static ChatRecordCover INSTANCE = new ChatRecordCover();

    public List<EnterpriseVo> conver2EnterpriseVo(List<ChatRecordEntity> chatRecordEntities, Map<Long, TbRegisterInfoEntity> infoEntityMap) {
        if (chatRecordEntities != null){
            List<EnterpriseVo> enterpriseVoList = new ArrayList<>();
            EnterpriseVo enterpriseVo;
            for (ChatRecordEntity chatRecordEntity : chatRecordEntities) {
                enterpriseVo =new EnterpriseVo();
                enterpriseVo.setId(chatRecordEntity.getId());
                enterpriseVo.setSessionId(chatRecordEntity.getSessionId());
                enterpriseVo.setName(infoEntityMap.get(chatRecordEntity.getSessionId()).getCompanyName());
                enterpriseVo.setLastChatTime(chatRecordEntity.getLastChatTime());
                enterpriseVo.setHasUnread(chatRecordEntity.getHasUnread());
                enterpriseVoList.add(enterpriseVo);
            }
            return enterpriseVoList;
        }
        return null;
    }
}
