package com.yjtech.wisdom.tourism.system.mapper;

import com.yjtech.wisdom.tourism.dto.area.AreaInfoVO;
import com.yjtech.wisdom.tourism.dto.area.AreaTreeNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ycwu
 */
public interface AreaMapper {

    /**
     * 获得区域树
     *
     * @return 区域树
     */
    List<AreaTreeNode> getAreaTree(@Param("areaCode") String areaCode);

    /**
     * 根据区域编码获得区域信息
     *
     * @param code 区域编码
     * @return 区域信息
     */
    AreaInfoVO getAreaInfoByCode(String code);

    /**
     * 获得省地市列表
     */
    List<AreaInfoVO> getPrefectureLevelCityList();

    /**
     * 获得列表
     */
    List<AreaInfoVO> getAreaList();

}