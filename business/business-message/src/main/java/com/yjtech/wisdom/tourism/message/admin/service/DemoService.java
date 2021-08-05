package com.yjtech.wisdom.tourism.message.admin.service;

import com.yjtech.wisdom.tourism.message.admin.dto.MessageCallDto;
import com.yjtech.wisdom.tourism.message.common.MessageCall;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author renguangqian
 * @date 2021/8/4 14:00
 */
@Service
public class DemoService implements MessageCall {
    @Override
    public List<MessageCallDto> queryEvent(Long[] ids) {
        return null;
    }
}
