package com.yjtech.wisdom.tourism.resource.toilet.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.resource.toilet.dto.ToiletPageQueryDto;
import com.yjtech.wisdom.tourism.resource.toilet.entity.ToiletEntity;
import com.yjtech.wisdom.tourism.resource.toilet.mapper.ToiletMapper;
import com.yjtech.wisdom.tourism.resource.toilet.vo.ToiletVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class ToiletService extends ServiceImpl<ToiletMapper, ToiletEntity> {


    public IPage<ToiletVo> queryForPage(ToiletPageQueryDto query) {
        return baseMapper.queryForPage(new Page(query.getPageNo(), query.getPageSize()), query);
    }

    /**
     * 跟新状态
     *
     * @param updateStatusParam
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(UpdateStatusParam updateStatusParam) {
        ToiletEntity entity = this.getById(updateStatusParam.getId());
        entity.setStatus(updateStatusParam.getStatus());
        this.updateById(entity);
    }

    public ToiletVo queryForDetail(Long id) {
        ToiletEntity byId = Optional.ofNullable(this.getById(id)).orElse(new ToiletEntity());
        ToiletVo wifiVo = new ToiletVo();
        BeanUtils.copyProperties(byId, wifiVo);
        return wifiVo;
    }

    /**
     * 设备编号是否唯一
     * @param deviceId
     * @param id
     * @return
     */
    public boolean driveExist(String deviceId, Long id) {
        LambdaQueryWrapper<ToiletEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ToiletEntity::getDeviceId, deviceId);
        if(Objects.nonNull(id)){
            queryWrapper.ne(ToiletEntity::getId, id);
        }
        int count = Optional.ofNullable(this.count(queryWrapper)).orElse(0);
        return count > 0 ? false : true;
    }
}
