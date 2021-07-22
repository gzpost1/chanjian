package com.yjtech.wisdom.tourism.command.service.event;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.command.entity.event.EventAppointEntity;
import com.yjtech.wisdom.tourism.command.mapper.event.EventAppointMapper;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 配置人员表 服务类
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-21
 */
@Service
public class EventAppointService extends ServiceImpl<EventAppointMapper, EventAppointEntity>{

    public Boolean queryUserAppoint(){
        QueryWrapper<EventAppointEntity> queryWrapper = new QueryWrapper<>();
        List<EventAppointEntity> list = this.list(queryWrapper);
        if(CollectionUtils.isEmpty(list)){
            return false;
        }
        for(EventAppointEntity entity : list){
            if(Objects.nonNull(entity.getAppointPersonnel())){
                return entity.getAppointPersonnel().contains(String.valueOf(SecurityUtils.getUserId()));
            }
        }
        return false;
    }
}
