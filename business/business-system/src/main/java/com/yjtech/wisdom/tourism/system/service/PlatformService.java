package com.yjtech.wisdom.tourism.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.enums.PlatformDefaultTimeTypeEnum;
import com.yjtech.wisdom.tourism.system.domain.Platform;
import com.yjtech.wisdom.tourism.system.mapper.PlatformMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liuhong
 * @date 2021-07-02 15:22
 */
@Service
public class PlatformService extends ServiceImpl<PlatformMapper, Platform> {

    @Autowired
    private AreaService areaService;

    /**
     * 获取平台信息
     */
    public Platform getPlatform() {
        List<Platform> platformList = list();
        // 如果没有初始化数据, 默认返回一个空值对象
        Platform platform = CollectionUtils.isEmpty(platformList) ? new Platform() : platformList.get(0);
        //设置时间筛选类型描述
        platform.setTimeSelectTypeDesc(PlatformDefaultTimeTypeEnum.getDescByValue(platform.getTimeSelectType()));
        return platform;
    }

    /**
     * 编辑平台信息，平台信息只有一个，该处处理逻辑为先清空表，再插入新数据
     *
     * @param platform 平台信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void savePlatform(Platform platform) {
        // 清空表数据
        baseMapper.truncateTable();
        platform.setAreaName(areaService.getLongAreaName(platform.getAreaCode()));
        // 插入平台信息
        this.save(platform);
    }
}
