package com.yjtech.wisdom.tourism.project.mapper;

import com.yjtech.wisdom.tourism.project.dto.ProjectOpenApiDTO;
import com.yjtech.wisdom.tourism.project.dto.ProjectOpenApiQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectOpenApiMapper {
    List<ProjectOpenApiDTO> getList(@Param("param") ProjectOpenApiQueryParam param);

    /**
     * 获取所有地区
     * @return
     */
    List<String> getAreaList();

    /**
     * 获取所有标签
     * @return
     */
    List<String> getLabelList();
}
