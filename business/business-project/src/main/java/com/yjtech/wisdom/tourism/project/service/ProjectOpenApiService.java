package com.yjtech.wisdom.tourism.project.service;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.project.dto.ProjectNumQueryParam;
import com.yjtech.wisdom.tourism.project.dto.ProjectOpenApiDTO;
import com.yjtech.wisdom.tourism.project.dto.ProjectOpenApiQueryParam;
import com.yjtech.wisdom.tourism.project.dto.ProjectQuery;
import com.yjtech.wisdom.tourism.project.entity.TbProjectInfoEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectLabelRelationEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectResourceEntity;
import com.yjtech.wisdom.tourism.project.mapper.ProjectOpenApiMapper;
import com.yjtech.wisdom.tourism.project.mapper.TbProjectInfoMapper;
import com.yjtech.wisdom.tourism.project.vo.ProjectAmountVo;
import com.yjtech.wisdom.tourism.project.vo.ProjectNumVO;
import org.apache.commons.collections.CollectionUtils;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProjectOpenApiService {
    @Autowired
    private ProjectOpenApiMapper apiMapper;

    /**
     * 查询列表
     *
     * @return
     */
    public List<ProjectOpenApiDTO> getList(ProjectOpenApiQueryParam param) {
        return apiMapper.getList(param);
    }

    /**
     * 详情
     *
     * @return
     */
    public ProjectOpenApiDTO queryForDetail(IdParam idParam) {
        ProjectOpenApiQueryParam param =  BeanMapper.copyBean(idParam, ProjectOpenApiQueryParam.class);
        List<ProjectOpenApiDTO> rows = apiMapper.getList(param);
        if (Objects.nonNull(rows) && rows.size() > 0) {
            return rows.get(0);
        }
        return null;
    }

    /**
     * 获取所有地区
     *
     * @return
     */
    public List<String> getAreaList() {
        return  apiMapper.getAreaList();
    }

    /**
     * 获取所有标签
     *
     * @return
     */
    public List<String> getLabelList() {
        return apiMapper.getLabelList();
    }

    /**
     * 根据开始时间和结束时间，查询总项目条数，总投资额
     * @param param
     * @return
     */
    public ProjectNumVO getAllProjectNum(ProjectNumQueryParam param) {
        return apiMapper.getAllProjectNum(param.getStartTime(),param.getEndTime());
    }

    /**
     * 根据开始时间和结束时间，查询总项目条数，总投资额（根据区县聚合）
     * @param param
     * @return
     */
    public List<ProjectNumVO> getProjectNumByAreaCode(ProjectNumQueryParam param) {
        return apiMapper.getAllProjectNumByAreaCode(param.getStartTime(),param.getEndTime());
    }
    /**
     * 根据开始时间和结束时间，查询总项目条数，总投资额（根据项目标签聚合）
     * @param param
     * @return
     */
    public List<ProjectNumVO> getProjectNumByLabel(ProjectNumQueryParam param) {
        return apiMapper.getAllProjectNumByLabel(param.getStartTime(),param.getEndTime());
    }
    /**
     * 根据开始时间和结束时间，查询总项目条数，总投资额（根据项目标签聚合）
     * @param param
     * @return
     */
    public List<ProjectNumVO> getProjectNumByMonth(ProjectNumQueryParam param) {
        return apiMapper.getAllProjectNumByMonth(param.getStartTime(),param.getEndTime());
    }

}
