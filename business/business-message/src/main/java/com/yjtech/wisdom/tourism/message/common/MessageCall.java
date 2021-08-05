package com.yjtech.wisdom.tourism.message.common;

import com.yjtech.wisdom.tourism.message.admin.dto.MessageCallDto;

import java.util.List;

/**
 * 消息中心接入方法
 *
 * @author renguangqian
 * @date 2021-8-4
 */
public interface MessageCall {

    /**
     * 通过事件id，查询事件信息
     *
     * @param ids
     * @return
     */
    List<MessageCallDto> queryEvent(Long[] ids);
}
