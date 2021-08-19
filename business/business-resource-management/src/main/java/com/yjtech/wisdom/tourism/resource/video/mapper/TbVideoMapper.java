package com.yjtech.wisdom.tourism.resource.video.mapper;

import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisMapper;
import com.yjtech.wisdom.tourism.resource.scenic.entity.ScenicEntity;
import com.yjtech.wisdom.tourism.resource.video.dto.ScreenVideoListDTO;
import com.yjtech.wisdom.tourism.resource.video.entity.TbVideoEntity;
import com.yjtech.wisdom.tourism.resource.video.vo.ScreenVideoQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 视频设备 Mapper 接口
 * </p>
 *
 * @author MJ~
 * @since 2021-08-02
 */
public interface TbVideoMapper extends BaseMybatisMapper<TbVideoEntity> {

    ScenicEntity querySecenicInfoById(String secenicId);

    /**
     * 查询景区监控列表
     * @param params
     * @return
     */
    List<ScreenVideoListDTO> queryScreenVideoList(@Param("params") ScreenVideoQueryVO params);

}
