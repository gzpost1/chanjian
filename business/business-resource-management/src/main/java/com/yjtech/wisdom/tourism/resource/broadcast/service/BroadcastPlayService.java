package com.yjtech.wisdom.tourism.resource.broadcast.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.BroadcastPlayEntity;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.dto.BroadcastPlayUpdateDto;
import com.yjtech.wisdom.tourism.resource.broadcast.mapper.BroadcastPlayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.yjtech.wisdom.tourism.common.utils.CommonPreconditions.checkCollectionEmpty;
import static com.yjtech.wisdom.tourism.common.utils.StringUtils.isNull;

@Service
public class BroadcastPlayService extends ServiceImpl<BroadcastPlayMapper, BroadcastPlayEntity> {

    @Autowired
    private BroadcastService broadcastService;


    public void updateBroadcast(BroadcastPlayUpdateDto updateDto) {
        /**
         * 1、如果修改了终端、文本、文件、播放次数需要重新执行新的任务才能生效
         * 2、单修改音量直接调用任务音量设置接口
         * */
        BroadcastPlayEntity byId = baseMapper.selectById(updateDto.getId());
        if (!isNull(byId)) {
            boolean isBroadcastIdsChange = false;
            String taskId = byId.getTaskId();
            Byte status = byId.getStatus();
            if (updateDto.getType().equals((byte) 0)) {
                /**实时*/
                if (!byId.getBroadcastIds().equals(updateDto.getBroadcastIds()) || !byId.getMicrophoneId().equals(updateDto.getMicrophoneId())) {
                    isBroadcastIdsChange = true;
                } else {
                    if (!byId.getVolume().equals(updateDto.getVolume()) && StringUtils.isNotBlank(taskId)) {
                        /**只修改了音量*/
                        setTaskVo(taskId, updateDto.getVolume());
                    }
                }
            } else if (updateDto.getType().equals((byte) 1)) {
                /**文件*/
                if (!byId.getBroadcastIds().equals(updateDto.getBroadcastIds()) || !byId.getMusicIds().equals(updateDto.getMusicIds())) {
                    isBroadcastIdsChange = true;
                } else {
                    if (!byId.getVolume().equals(updateDto.getVolume()) && StringUtils.isNotBlank(taskId)) {
                        /**只修改了音量*/
                        setTaskVo(taskId, updateDto.getVolume());
                    }
                }
            } else {
                /**文本*/
                if (!byId.getBroadcastIds().equals(updateDto.getBroadcastIds()) || !byId.getText().equals(updateDto.getText())
                        || !byId.getRepeatTime().equals(updateDto.getRepeatTime())) {
                    isBroadcastIdsChange = true;
                } else {
                    if (!byId.getVolume().equals(updateDto.getVolume()) && StringUtils.isNotBlank(taskId)) {
                        /**只修改了音量*/
                        setTaskVo(taskId, updateDto.getVolume());
                    }
                }
            }
            if (isBroadcastIdsChange) {
                /**停止任务*/
//                broadcastService.stopTask(StopTaskVo.builder().id(byId.getId()).build());
                taskId = "";
                status = 0;
            }

            BroadcastPlayEntity entity = BeanMapper.map(updateDto, BroadcastPlayEntity.class);
            entity.setTaskId(taskId);
            entity.setStatus(status);
            baseMapper.updateById(entity);
        }
    }

    public void setTaskVo(String taskId, Integer volume) {

    }

    public List<BroadcastPlayEntity> queryPlay(Integer type) {
        LambdaQueryWrapper<BroadcastPlayEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BroadcastPlayEntity::getType, type);
        List<BroadcastPlayEntity> list = baseMapper.selectList(wrapper);
        if (checkCollectionEmpty(list)) {
            return new ArrayList<>();
        }
        return list;
    }

}