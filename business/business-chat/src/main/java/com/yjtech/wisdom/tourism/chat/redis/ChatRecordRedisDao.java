package com.yjtech.wisdom.tourism.chat.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author han
 * @createTime 2022/5/17 10:17
 * @description  使用redis提高相关接口访问效率
 */
@Repository
public class ChatRecordRedisDao {

    private final static String keyTemplate = "message_unread:%s";

    @Resource
    private RedisTemplate redisTemplate;

    public void add(Long initiatorId, Long sessionId) {
        String redisKey = formatKey(initiatorId);
        redisTemplate.opsForSet().add(redisKey, sessionId);
    }

    public void remove(Long initiatorId, Long sessionId) {
        String redisKey = formatKey(initiatorId);
        redisTemplate.opsForSet().remove(redisKey, sessionId);
    }

    public Set<Long> getAll(Long initiatorId) {
        String redisKey = formatKey(initiatorId);
        Set<Long> result = redisTemplate.opsForSet().members(redisKey);
        return result;
    }
    
    private static String formatKey(Long initiatorId) {
        return String.format(keyTemplate, initiatorId);
    }


}
