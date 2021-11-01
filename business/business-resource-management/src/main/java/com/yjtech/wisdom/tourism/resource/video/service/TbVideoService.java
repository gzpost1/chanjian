package com.yjtech.wisdom.tourism.resource.video.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisServiceImpl;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import com.yjtech.wisdom.tourism.resource.video.bo.VideoGuideBo;
import com.yjtech.wisdom.tourism.resource.video.dto.ScreenVideoListDTO;
import com.yjtech.wisdom.tourism.resource.video.entity.TbVideoEntity;
import com.yjtech.wisdom.tourism.resource.video.mapper.TbVideoMapper;
import com.yjtech.wisdom.tourism.resource.video.vo.ScreenVideoQueryVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 视频设备 服务实现类
 *
 * @author Mujun
 * @since 2021-08-02
 */
@Service
public class TbVideoService extends BaseMybatisServiceImpl<TbVideoMapper, TbVideoEntity>  {

    //根据景区id查询监控
    public IPage<TbVideoEntity> queryVideoByScenicId(ScenicScreenQuery query){
        LambdaQueryWrapper<TbVideoEntity> queryWrapper = new LambdaQueryWrapper<TbVideoEntity>();
        queryWrapper.eq(TbVideoEntity::getSecenicId, query.getScenicId());
        queryWrapper.orderByDesc(TbVideoEntity::getEquipStatus);
        queryWrapper.orderByDesc(TbVideoEntity::getSort);
        IPage page = page(new Page(query.getPageNo(), query.getPageSize()), queryWrapper);
        return page;
    }

    /**
     * 查询景区监控列表
     * @param vo
     * @return
     */
    public List<ScreenVideoListDTO> queryScreenVideoList(ScreenVideoQueryVO vo){
        return baseMapper.queryScreenVideoList(vo);
    }

    public String importExcel(List<VideoGuideBo> videoGuideBos,Long spotId) {
        List<TbVideoEntity> tbVideoEntities = BeanMapper.mapList(videoGuideBos, TbVideoEntity.class);
        if (CollectionUtils.isNotEmpty(tbVideoEntities)) {
            //查询数据
            List<TbVideoEntity> entityList = list(new QueryWrapper());
            //判断数据是否有重复值
            Map<String, Long> deviceIdMap = videoGuideBos.stream()
                    .collect(Collectors.groupingBy(VideoGuideBo::getDeviceId, Collectors.counting()));

            for (Map.Entry<String, Long> item : deviceIdMap.entrySet()) {
                if (item.getValue() > 1) {
                    return "设备编号" + item.getKey() + "重复";
                } else {
                    Map<String, TbVideoEntity> map = entityList.stream().filter(Objects::nonNull)
                            .collect(Collectors.toMap(TbVideoEntity::getDeviceId, e -> e));
                    if (map.containsKey(item.getKey())) {
                        return "设备编号" + item.getKey() + "已存在";
                    }
                }
            }
            //移除无效信息
            tbVideoEntities.removeAll(Collections.singleton(null));
            for (TbVideoEntity tbVideoEntity : tbVideoEntities) {
                tbVideoEntity.setSecenicId(spotId);
            }
            //批量创建
            saveBatch(tbVideoEntities);
        }
        return null;
    }
}
