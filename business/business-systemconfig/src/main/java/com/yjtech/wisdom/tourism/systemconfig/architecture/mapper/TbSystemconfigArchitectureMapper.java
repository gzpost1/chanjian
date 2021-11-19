package com.yjtech.wisdom.tourism.systemconfig.architecture.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.systemconfig.architecture.dto.MenuTreeNode;
import com.yjtech.wisdom.tourism.systemconfig.architecture.dto.SystemconfigArchitectureDto;
import com.yjtech.wisdom.tourism.systemconfig.architecture.dto.SystemconfigArchitecturePageQuery;
import com.yjtech.wisdom.tourism.systemconfig.architecture.entity.TbSystemconfigArchitectureEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbSystemconfigArchitectureMapper extends BaseMapper<TbSystemconfigArchitectureEntity> {
    IPage<SystemconfigArchitectureDto> queryForPage(Page page, @Param("params") SystemconfigArchitecturePageQuery query);

    Integer queryChildsByParent(Long parentId);
    Integer getChildMaxNumByParendId(Long parentId);

    TbSystemconfigArchitectureEntity getFirstByParent(int parentId);

    BaseVO queryMaxAndMinByParendId(Long parentId);

    void updateSortNum(@Param("params") UpdateStatusParam updateStatusParam);

    String queryNameByPingtai();


    List<MenuTreeNode> getAreaTree(@Param("parentId") int parentId ,@Param("type") Integer type);

    TbSystemconfigArchitectureEntity getArchitecutueSortNum(@Param("params") UpdateStatusParam updateStatusParam);
}