package com.yjtech.wisdom.tourism.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.system.domain.TagEntity;
import com.yjtech.wisdom.tourism.system.vo.TagQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签管理(TbTag)表数据库访问层
 *
 * @author horadirm
 * @since 2022-03-11 09:59:08
 */
public interface TagMapper extends BaseMapper<TagEntity> {


    /**
     * 批量新增更新
     *
     * @param params
     */
    void insertUpdateBatch(@Param("params") List<TagEntity> params);

    /**
     * 查询列表
     *
     * @param page
     * @param params
     * @return
     */
    List<TagEntity> queryForList(Page page, @Param("params")TagQueryVO params);

    /**
     * 根据id查询信息
     *
     * @param id
     * @return
     */
    TagEntity queryById(Long id);

}