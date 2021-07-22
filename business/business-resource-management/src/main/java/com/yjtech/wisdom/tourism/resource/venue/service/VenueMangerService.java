package com.yjtech.wisdom.tourism.resource.venue.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisServiceImpl;
import com.yjtech.wisdom.tourism.resource.venue.dto.VenueDto;
import com.yjtech.wisdom.tourism.resource.venue.dto.VenueScaleDto;
import com.yjtech.wisdom.tourism.resource.venue.entity.VenueEntity;
import com.yjtech.wisdom.tourism.resource.venue.mapper.VenueMapper;
import com.yjtech.wisdom.tourism.resource.venue.vo.VenueVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 文博场馆管理
 *
 * @author renguangqian
 * @date 2021/7/21 11:25
 */
@Service
public class VenueMangerService extends BaseMybatisServiceImpl<VenueMapper, VenueEntity> {

    /**
     * 查询文博场馆列表_分页
     *
     * @param vo
     * @return
     */
    public IPage<VenueDto> queryPage(VenueVo vo) {
        return baseMapper.selectPage(
                new Page<>(vo.getPageNo(), vo.getPageSize()),
                new LambdaQueryWrapper<VenueEntity>()
                    .like(!StringUtils.isEmpty(vo.getVenueName()), VenueEntity::getVenueName, vo.getVenueName())
                    .eq(VenueEntity::getStatus, 1)
                    .orderByDesc(VenueEntity::getUpdateTime)
                )
                .convert(item -> JSONObject.parseObject(JSONObject.toJSONString(item), VenueDto.class));
    }

    /**
     * 查询文博场馆分布比列
     *
     * @return
     */
    public List<VenueScaleDto> queryScale() {
        QueryWrapper<VenueEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("select count(id) as venueTypeNumber, venueType");
        queryWrapper.eq("status", 1);
        queryWrapper.groupBy("venueType");

        // 分组查询 各类场馆的数量
        List<VenueEntity> venueEntityList = baseMapper.selectList(queryWrapper);
        // 查询场馆 总数
        Integer total = venueEntityList.stream().mapToInt(VenueEntity::getVenueTypeNumber).sum();

        List<VenueScaleDto> result = Lists.newArrayList();

        // 计算各类场馆比例
        venueEntityList.forEach(item -> {
            // 场馆类型名称_由于该值是数据字典，后期可能需要单独取值进行改动
            String venueType = item.getVenueType();
            // 对应场馆类型的数量
            Integer venueTypeNumber = item.getVenueTypeNumber();

            //计算比例
            VenueScaleDto venueScaleDto = VenueScaleDto.builder()
                    .name(venueType)
                    .scale(MathUtil.calPercent(new BigDecimal(venueTypeNumber), new BigDecimal(total), 2).toString())
                    .build();

            result.add(venueScaleDto);
        });
        return result;
    }


}
