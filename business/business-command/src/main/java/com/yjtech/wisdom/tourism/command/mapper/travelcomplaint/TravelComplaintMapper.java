package com.yjtech.wisdom.tourism.command.mapper.travelcomplaint;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintListDTO;
import com.yjtech.wisdom.tourism.command.entity.travelcomplaint.TravelComplaintEntity;
import com.yjtech.wisdom.tourism.command.vo.TravelComplaintQueryVO;
import org.apache.ibatis.annotations.Param;

/**
 * 旅游投诉(TbTravelComplaint)表数据库访问层
 *
 * @author horadirm
 * @since 2021-07-21 09:03:47
 */
public interface TravelComplaintMapper extends BaseMapper<TravelComplaintEntity> {


    /**
     * 根据id查询实体信息
     * @param id
     * @return
     */
    TravelComplaintEntity queryEntityById(@Param("id") Long id);

    /**
     * 查询分页
     * @param page
     * @param params
     * @return
     */
    IPage<TravelComplaintListDTO> queryForPage(Page page, @Param("params") TravelComplaintQueryVO params);



}
