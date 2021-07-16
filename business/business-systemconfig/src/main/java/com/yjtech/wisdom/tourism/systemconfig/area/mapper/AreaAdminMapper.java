package com.yjtech.wisdom.tourism.systemconfig.area.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.systemconfig.area.entity.AreaAdminEntity;
import com.yjtech.wisdom.tourism.systemconfig.area.query.AreaAdminQuery;
import com.yjtech.wisdom.tourism.systemconfig.area.vo.AreaAdminVO;
import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 行政区域信息表 Mapper 接口
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-02
 */
public interface AreaAdminMapper extends MyBaseMapper<AreaAdminEntity> {

    IPage<AreaAdminVO> queryForPage(IPage<AreaAdminVO> page, @Param("params") AreaAdminQuery query);

    List<AreaAdminVO> queryForPage( @Param("params") AreaAdminQuery query);
}
