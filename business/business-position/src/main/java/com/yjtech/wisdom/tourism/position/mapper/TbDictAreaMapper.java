package com.yjtech.wisdom.tourism.position.mapper;

import com.yjtech.wisdom.tourism.dto.area.AreaTreeNode;
import com.yjtech.wisdom.tourism.position.entity.TbDictAreaEntity;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
/**
 * <p>
 * 区域信息表 Mapper 接口
 * </p>
 *
 * @author MJ~
 * @since 2020-10-27
 */
public interface TbDictAreaMapper extends BaseMybatisMapper<TbDictAreaEntity> {

    List<AreaTreeNode> getAreaTree(@Param("areaCode") String areaCode);
}
