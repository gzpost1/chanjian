package com.yjtech.wisdom.tourism.systemconfig.chart.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.MenuChartQueryDto;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.SystemConfigChartQueryPageDto;
import com.yjtech.wisdom.tourism.systemconfig.chart.vo.SystemconfigChartsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.systemconfig.chart.entity.SystemconfigChartsEntity;
import com.yjtech.wisdom.tourism.systemconfig.chart.mapper.SystemconfigChartsMapper;

import javax.xml.bind.TypeConstraintException;
import java.util.List;
import java.util.Objects;

@Service
public class SystemconfigChartsService extends ServiceImpl<SystemconfigChartsMapper, SystemconfigChartsEntity> {

    /**
     * 分页
     *
     * @param query
     * @return
     */
    public IPage<SystemconfigChartsVo> queryForPage(SystemConfigChartQueryPageDto query) {
        return baseMapper.queryForPage(new Page(query.getPageNo(), query.getPageSize()), query);
    }

    public SystemconfigChartsVo queryForDetail(Long id) {
        SystemconfigChartsEntity byId = this.getById(id);
        if (Objects.isNull(byId)) {
            new CustomException(ErrorCode.BUSINESS_EXCEPTION, "查询数据不存在");
        } else {
            SystemconfigChartsVo systemconfigChartsVo = new SystemconfigChartsVo();
            BeanUtils.copyProperties(byId, systemconfigChartsVo);
            return systemconfigChartsVo;
        }

        return null;
    }

    public List<SystemconfigChartsVo> queryChartListForMenu(MenuChartQueryDto queryDto) {
        return this.baseMapper.queryChartListForMenu(queryDto);
    }

    public Integer findChartMenusNum(Long id) {
        return this.baseMapper.findChartMenusNum(id);
    }

    public Integer findMenuIsExistChart(Long id) {
        return this.baseMapper.findMenuIsExistChart(id);
    }
}

