package com.yjtech.wisdom.tourism.system.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.dto.area.AreaInfoVO;
import com.yjtech.wisdom.tourism.dto.area.AreaTreeNode;
import com.yjtech.wisdom.tourism.system.mapper.AreaMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author wuyongchong
 * @date 2019/9/3
 */
@Service
public class AreaService {

    @Autowired
    private AreaMapper areaMapper;

    private static final Cache<String, List<AreaInfoVO>> PREFECTURE_LEVEL_CITY_LIST_CACHE = CacheBuilder.newBuilder()
            .expireAfterAccess(60, TimeUnit.MINUTES)
            .build();

    public List<AreaTreeNode> getAreaTree(String areaCode) {
        return areaMapper.getAreaTree(areaCode);
    }

    public AreaInfoVO getAreaInfoByCode(String code) {
        return Optional.ofNullable(areaMapper.getAreaInfoByCode(code))
                .orElseThrow(() -> new CustomException(
                        ErrorCode.NOT_FOUND, "区域信息找不到"));
    }

    public String getLongAreaName(String code) {
        AreaInfoVO infoByCode = getAreaInfoByCode(code);
        if (StringUtils.isBlank(infoByCode.getProvinceName())) {
            return infoByCode.getName();
        }
        StringBuilder builder = new StringBuilder(infoByCode.getProvinceName());
        if (StringUtils.isNotBlank(infoByCode.getCityName())) {
            builder.append("/").append(infoByCode.getCityName());
        }
        if (StringUtils.isNotBlank(infoByCode.getCountyName())) {
            builder.append("/").append(infoByCode.getCountyName());
        }
        return builder.toString();
    }

    public List<AreaInfoVO> getPrefectureLevelProvinceList() {

        List<AreaInfoVO> prefectureLevelCityList = PREFECTURE_LEVEL_CITY_LIST_CACHE
                .getIfPresent("prefectureLevelCityList");

        if (CollectionUtils.isEmpty(prefectureLevelCityList)) {
            prefectureLevelCityList = Optional
                    .ofNullable(areaMapper.getPrefectureLevelCityList()).orElse(
                            Lists.newArrayList());
            if (CollectionUtils.isNotEmpty(prefectureLevelCityList)) {
                PREFECTURE_LEVEL_CITY_LIST_CACHE
                        .put("prefectureLevelCityList", prefectureLevelCityList);
            }
        }

            prefectureLevelCityList = prefectureLevelCityList.stream()
                    .filter(item -> Objects.isNull(item.getCityCode())).collect(
                            Collectors.toList());

        return prefectureLevelCityList;
    }

    public List<AreaInfoVO> getPrefectureLevelCityList(String provinceCode) {

        List<AreaInfoVO> prefectureLevelCityList = PREFECTURE_LEVEL_CITY_LIST_CACHE
                .getIfPresent("prefectureLevelCityList");

        if (CollectionUtils.isEmpty(prefectureLevelCityList)) {
            prefectureLevelCityList = Optional
                    .ofNullable(areaMapper.getPrefectureLevelCityList()).orElse(
                            Lists.newArrayList());
            if (CollectionUtils.isNotEmpty(prefectureLevelCityList)) {
                PREFECTURE_LEVEL_CITY_LIST_CACHE
                        .put("prefectureLevelCityList", prefectureLevelCityList);
            }
        }

        if (StringUtils.isNotBlank(provinceCode)) {
            prefectureLevelCityList = prefectureLevelCityList.stream()
                    .filter(item -> item.getParentCode().equals(provinceCode)).collect(
                            Collectors.toList());
        }

        return prefectureLevelCityList;
    }

    public List<AreaInfoVO> getAreaList() {
        return Optional.ofNullable(areaMapper.getAreaList()).orElse(Lists.newArrayList());
    }

    public String getAreaCodeByName(List<AreaInfoVO> areaList, String areaName) {
        if (CollectionUtils.isEmpty(areaList) || StringUtils.isBlank(areaName)) {
            return null;
        }
        String areaCode = null;
        for (AreaInfoVO areaInfoVO : areaList) {
            if (areaName.contains(areaInfoVO.getName()) || areaInfoVO.getName()
                    .contains(areaName)) {
                return areaInfoVO.getCode();
            }
        }
        return areaCode;
    }

}
