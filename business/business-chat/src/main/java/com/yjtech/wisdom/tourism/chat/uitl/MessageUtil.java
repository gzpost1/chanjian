package com.yjtech.wisdom.tourism.chat.uitl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjtech.wisdom.tourism.chat.dto.SendMessage;
import com.yjtech.wisdom.tourism.common.utils.MD5Utils;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.ScreenLoginUser;
import com.yjtech.wisdom.tourism.redis.RedisCache;

/**
 * @author han
 * @createTime 2022/5/12 16:34
 * @description
 */
public class MessageUtil {

    private final static String DIRE = "md5UserId:";

    public static String getMd5Userid(Long fromId, Long toId, RedisCache redisCache) {
        if (fromId == null || toId == null) {
            return null;
        }
        String resultUid = fromId <= toId ? "" + fromId + "," + toId : "" + toId + "," + fromId;
        String md5UserId = redisCache.getCacheObject(DIRE + resultUid);
        if (md5UserId != null) {
            return md5UserId;
        }
        md5UserId = MD5Utils.makeMD5(resultUid);
        redisCache.setCacheObject(DIRE + resultUid,md5UserId);
        return md5UserId;
    }

    public static String getMessage(int messageType,ScreenLoginUser currentUser, Object message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setFromId(currentUser.getId());
        sendMessage.setFromName(currentUser.getCompanyName());
        sendMessage.setMessage(message);
        sendMessage.setMessageType(messageType);
        ObjectMapper objectMapper = new ObjectMapper();
        String repStr = null;
        try {
            repStr = objectMapper.writeValueAsString(sendMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return repStr;
    }

}
