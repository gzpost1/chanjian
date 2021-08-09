package com.yjtech.wisdom.tourism.resource.scenic.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.common.utils.EncryptUtil;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.resource.scenic.entity.ScenicEntity;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicBaseVo;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicScreenPageVo;
import com.yjtech.wisdom.tourism.resource.scenic.mapper.ScenicMapper;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicPageQuery;
import com.yjtech.wisdom.tourism.weather.service.WeatherService;
import com.yjtech.wisdom.tourism.weather.vo.WeatherInfoVO;
import com.yjtech.wisdom.tourism.weather.web.WeatherQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yjtech.wisdom.tourism.common.utils.StringUtils.isNull;

@Service
public class ScenicService extends ServiceImpl<ScenicMapper, ScenicEntity> {

    @Autowired
    private WeatherService weatherService;

    public IPage<ScenicEntity> queryForPage(ScenicPageQuery query) {
        LambdaQueryWrapper wrapper = getCommonWrapper(query.getName(), query.getStatus()).orderByDesc(ScenicEntity::getCreateTime);
        return page(new Page<>(query.getPageNo(), query.getPageSize()), wrapper);
    }


    public IPage<ScenicScreenPageVo> queryScreenForPage(ScenicPageQuery query) {
        LambdaQueryWrapper wrapper = getCommonWrapper(query.getName(), query.getStatus()).orderByDesc(ScenicEntity::getLevel);
        IPage page = page(new Page<>(query.getPageNo(), query.getPageSize()), wrapper)
                .convert(item -> BeanMapper.copyBean(item, ScenicScreenPageVo.class));
        List<ScenicScreenPageVo> records = page.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            records.forEach(item -> {
                String comfort = (item.getEnterNum() / item.getBearCapacity()) >= item.getComfortWarnRate().intValue() ? "拥挤" : "舒适";
                //设置舒适类别
                item.setComfortCategory(comfort);
                //天气
                WeatherQuery weatherQuery = new WeatherQuery();
                weatherQuery.setLatitude(item.getLatitude());
                weatherQuery.setLongitude(item.getLongitude());
                item.setWeatherInfoVO(queryWeatherByAreaCode(weatherQuery));
            });
        }
        return page;
    }

    public List<ScenicBaseVo> queryLevelDistribution() {
        LambdaQueryWrapper<ScenicEntity> wrapper = getCommonWrapper(null, (byte) 1);
        List<ScenicEntity> list = list(wrapper);
        ArrayList<ScenicBaseVo> vos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            Map<String, List<ScenicEntity>> map = list.stream().collect(Collectors.groupingBy(ScenicEntity::getLevel));
            for (Map.Entry<String, List<ScenicEntity>> next : map.entrySet()) {
                vos.add(ScenicBaseVo.builder()
                        .name(next.getKey())
                        .value(String.valueOf(MathUtil.calPercent(new BigDecimal(next.getValue().size()), BigDecimal.valueOf(list.size()), 3).doubleValue()))
                        .build());
            }
        }
        return vos;
    }

    //获取天气
    private WeatherInfoVO queryWeatherByAreaCode(WeatherQuery query) {
        try {
            Map<String, String> map = Maps.newHashMap();
            map.put("location", query.getLongitude() + "," + query.getLatitude());
            String code = EncryptUtil.makeMD5(query.getLatitude() + query.getLongitude());
            return weatherService.queryWeatherInfo(map, code);
        } catch (Exception e) {
            log.error("景区获取天气信息失败......", e);
            e.printStackTrace();
        }
        return null;
    }

    //公共分页lambda
    private LambdaQueryWrapper<ScenicEntity> getCommonWrapper(String name, Byte status) {
        return new LambdaQueryWrapper<ScenicEntity>()
                .like(StringUtils.isNotBlank(name), ScenicEntity::getName, name)
                .eq(!isNull(status), ScenicEntity::getStatus, status);
    }
}
