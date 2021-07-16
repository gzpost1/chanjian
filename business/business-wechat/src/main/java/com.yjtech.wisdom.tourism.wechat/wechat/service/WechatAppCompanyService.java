package com.yjtech.wisdom.tourism.wechat.wechat.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import com.yjtech.wisdom.tourism.wechat.wechat.dto.BindAppCompanyDto;
import com.yjtech.wisdom.tourism.wechat.wechat.dto.CompanyDto;
import com.yjtech.wisdom.tourism.wechat.wechat.entity.WechatAppCompany;
import com.yjtech.wisdom.tourism.wechat.wechat.mapper.WechatAppCompanyMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: wuyongchong
 * @date: 2020/8/27 11:09
 */
@Service
public class WechatAppCompanyService extends ServiceImpl<WechatAppCompanyMapper, WechatAppCompany> {

    @Autowired
    private WechatAppCompanyMapper wechatAppCompanyMapper;

    public List<WechatAppCompany> getCompanyListByAppId(String appId) {
        List<WechatAppCompany> selectList = wechatAppCompanyMapper.selectList(
                new LambdaQueryWrapper<WechatAppCompany>().eq(WechatAppCompany::getAppId, appId));
        return selectList;
    }

    public List<WechatAppCompany> getAppListByCompanyId(Long companyId) {
        List<WechatAppCompany> selectList = wechatAppCompanyMapper.selectList(
                new LambdaQueryWrapper<WechatAppCompany>()
                        .eq(WechatAppCompany::getCompanyId, companyId));
        return selectList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void bindAppCompanys(BindAppCompanyDto dto) {

        List<WechatAppCompany> oldList = getCompanyListByAppId(dto.getAppId());

        if (CollectionUtils.isNotEmpty(oldList)) {

            Set<String> oldCompanyIdSet = oldList.stream()
                    .map(item -> String.valueOf(item.getCompanyId()))
                    .collect(Collectors.toSet());

            Set<String> differenceSet = oldCompanyIdSet;

            if (CollectionUtils.isNotEmpty(dto.getCompanyList())) {

                Set<String> newCompanyIdSet = dto.getCompanyList().stream()
                        .map(item -> String.valueOf(item.getCompanyId())).collect(
                                Collectors.toSet());

                differenceSet = Sets.difference(oldCompanyIdSet, newCompanyIdSet);
            }
            if (CollectionUtils.isNotEmpty(differenceSet)) {
                List<Long> companyIdList = differenceSet.stream().map(item -> Long.valueOf(item))
                        .collect(Collectors.toList());
                wechatAppCompanyMapper.unbindScenicApp(companyIdList,dto.getAppId());
            }
        }

        wechatAppCompanyMapper.delete(new LambdaQueryWrapper<WechatAppCompany>()
                .eq(WechatAppCompany::getAppId, dto.getAppId()));

        if (CollectionUtils.isNotEmpty(dto.getCompanyList())) {
            List<CompanyDto> companyList = dto.getCompanyList();
            for (CompanyDto companyDto : companyList) {
                WechatAppCompany entity = new WechatAppCompany();
                entity.setAppId(dto.getAppId());
                entity.setAppName(dto.getAppName());
                entity.setCompanyId(companyDto.getCompanyId());
                entity.setCompanyName(companyDto.getCompanyName());
                wechatAppCompanyMapper.insert(entity);
            }
        }
    }
}
