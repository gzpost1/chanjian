package com.yjtech.wisdom.tourism.systemconfig.temp.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.systemconfig.temp.dto.SystemconfigTempPageQueryDto;
import com.yjtech.wisdom.tourism.systemconfig.temp.entity.SystemconfigTempEntity;
import com.yjtech.wisdom.tourism.systemconfig.temp.mapper.SystemconfigTempMapper;
import com.yjtech.wisdom.tourism.systemconfig.temp.vo.SystemconfigTempVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SystemconfigTempService extends ServiceImpl<SystemconfigTempMapper, SystemconfigTempEntity> {


    public int batchInsert(List<SystemconfigTempEntity> list) {
        return baseMapper.batchInsert(list);
    }

    /**
     * 分页
     *
     * @param query
     * @return
     */
    public IPage<SystemconfigTempVo> queryForPage(SystemconfigTempPageQueryDto query) {
        return baseMapper.queryForPage(new Page(query.getPageNo(), query.getPageSize()), query);
    }

    public SystemconfigTempVo queryForDetail(Long id) {
        SystemconfigTempEntity byId = this.baseMapper.getById(id);
        if (Objects.isNull(byId)) {
            new CustomException(ErrorCode.BUSINESS_EXCEPTION, "查询数据不存在");
        } else {
            SystemconfigTempVo systemconfigChartsVo = new SystemconfigTempVo();
            BeanUtils.copyProperties(byId, systemconfigChartsVo);
            return systemconfigChartsVo;
        }

        return null;

    }

    public List<SystemconfigTempVo> queryForTempList() {
        return baseMapper.queryForTempList();
    }

    public Integer findTempMenusNum(Long id) {
        return this.baseMapper.findTempMenusNum(id);
    }

    public boolean findNameExist(String name, Long id) {
        LambdaQueryWrapper<SystemconfigTempEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemconfigTempEntity::getName, name);
        queryWrapper.ne(SystemconfigTempEntity::getId,id);

        int count = this.count(queryWrapper);
        return count > 0 ? false : true;
    }
}
