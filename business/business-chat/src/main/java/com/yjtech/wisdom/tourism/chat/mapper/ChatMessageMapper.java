package com.yjtech.wisdom.tourism.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.chat.dto.MessageHistoryQuery;
import com.yjtech.wisdom.tourism.chat.entity.ChatMessageEntity;

import java.util.List;

public interface ChatMessageMapper extends BaseMapper<ChatMessageEntity> {

    List<ChatMessageEntity> selectMessageHistory(MessageHistoryQuery messageHistoryQuery);
}
