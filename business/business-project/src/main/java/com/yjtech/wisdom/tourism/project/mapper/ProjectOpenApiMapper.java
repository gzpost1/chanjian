package com.yjtech.wisdom.tourism.project.mapper;

import com.yjtech.wisdom.tourism.project.dto.ProjectOpenApiDTO;
import com.yjtech.wisdom.tourism.project.dto.ProjectOpenApiQueryParam;
import com.yjtech.wisdom.tourism.project.vo.ProjectNumVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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


    ProjectNumVO getAllProjectNum(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

     List<ProjectNumVO> getAllProjectNumByAreaCode(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

    List<ProjectNumVO> getAllProjectNumByLabel(@Param("startTime") Date startTime,@Param("endTime") Date endTime);
    List<ProjectNumVO> getAllProjectNumByMonth(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

}
