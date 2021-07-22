package com.yjtech.wisdom.tourism.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.system.domain.Icon;
import com.yjtech.wisdom.tourism.system.domain.IconDetail;
import com.yjtech.wisdom.tourism.system.domain.IconSpotEnum;
import com.yjtech.wisdom.tourism.system.mapper.IconMapper;
import com.yjtech.wisdom.tourism.system.vo.IconQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author liuhong
 * @date 2021-07-05 9:12
 */
@Service
public class IconService extends ServiceImpl<IconMapper, Icon> {
    /**
     * 图标类型字典key
     */
    private static final String DICT_KEY_CONFIG_IMG_TYPE = "config_img_type";

    /**
     * 点位类型字典key
     */
    private static final String DICT_KEY_CONFIG_SPOT_TYPE = "config_spot_type";

    @Autowired
    private SysDictDataService dictDataService;

    @Transactional(rollbackFor = Exception.class)
    public void replaceInto(List<Icon> list) {
        baseMapper.replaceBatchInto(list);
    }

    public void saveIcon(Icon icon) {
        replaceInto(Lists.newArrayList(icon));
    }

    public Icon getIconById(Long id) {
        return Optional.ofNullable(this.queryForDetail(id)).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public IPage<Icon> queryForPage(IconQuery query) {
        return baseMapper.queryForPage(new Page<>(query.getPageNo(), query.getPageSize()),
                DICT_KEY_CONFIG_SPOT_TYPE, DICT_KEY_CONFIG_IMG_TYPE, query.getType());
    }

    public Icon queryForDetail(Long id) {
        return baseMapper.queryForDetail(id, DICT_KEY_CONFIG_SPOT_TYPE, DICT_KEY_CONFIG_IMG_TYPE);
    }

    public boolean typeExists(String type, Long iconId) {
        if (null == iconId) {
            int count = this
                    .count(new QueryWrapper<Icon>().and(qw -> {
                        qw.eq("type", type);
                        return qw;
                    }));
            return count >= 1;
        } else {
            int count = this.count(new QueryWrapper<Icon>().and(qw -> {
                qw.eq("type", type);
                return qw;
            }).ne("id", iconId));
            return count >= 1;
        }
    }

    public List<Icon> querMenuIconList() {
        return this.baseMapper.querMenuIconList();
    }

    /**
     * 通过点位类型及当前状态查询当前状态对应的图标url
     * @param iconSpotEnum 点位类型
     * @param status 当前状态
     * @return 图标url
     */
    public String queryIconUrl(IconSpotEnum iconSpotEnum, String status) {
        if (Objects.isNull(iconSpotEnum) || StringUtils.isEmpty(status)) {
            return null;
        }

        Icon icon = baseMapper.queryIconByType(iconSpotEnum.getValue());

        if (Objects.isNull(icon)) {
            return null;
        }

        return icon.getUrl();
    }

}
