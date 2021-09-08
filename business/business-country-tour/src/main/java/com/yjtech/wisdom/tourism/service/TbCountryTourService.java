package com.yjtech.wisdom.tourism.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.bean.SelectVo;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.entity.TbCountryTour;
import com.yjtech.wisdom.tourism.mapper.TbCountryTourMapper;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisServiceImpl;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import com.yjtech.wisdom.tourism.system.domain.IconSpotEnum;
import com.yjtech.wisdom.tourism.system.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 *
 */
@Service
public class TbCountryTourService extends BaseMybatisServiceImpl<TbCountryTourMapper, TbCountryTour> {
    private DateFormat format = new SimpleDateFormat("HH:mm");

    @Autowired
    private IconService iconService;

    public List<SelectVo> statistic() {
        return baseMapper.statistic();
    }

    @Transactional(readOnly = true)
    public Page<TbCountryTour> pageForScreen(PageQuery param) {
        Page<TbCountryTour> page = new Page<>(param.getPageNo(), param.getPageSize());
        List<OrderItem> orderItems = new LinkedList<>();
        if (param.getAscs() != null && param.getAscs().length > 0) {
            Arrays.asList(param.getAscs()).forEach(bean -> {
                orderItems.add(OrderItem.asc(bean));
            });
        }
        if (param.getDescs() != null && param.getDescs().length > 0) {
            Arrays.asList(param.getDescs()).forEach(bean -> {
                orderItems.add(OrderItem.desc(bean));
            });
        }
        page.setOrders(orderItems);
        TbCountryTour entity = BeanMapper.copyBean(param, getTClass());
        page.setRecords(baseMapper.listExtra(page, entity));
        for (TbCountryTour record : page.getRecords()) {
            record.setOpenTimeStr(makeOpenTimeStr(record));
        }
        for (TbCountryTour record : page.getRecords()) {
            record.setIconUrl(iconService.queryIconUrl(IconSpotEnum.COUNTRY, "1"));
        }
        return page;
    }

    public String makeOpenTimeStr(TbCountryTour countryTour) {
        String str;
        String month;
        String time;
        if (countryTour.getOpenStartMonth() == null || countryTour.getOpenEndMonth() == null) {
            month = "";
        } else if (countryTour.getOpenStartMonth() - countryTour.getOpenEndMonth() == Math.abs(11) || countryTour.getOpenStartMonth().equals(countryTour.getOpenEndMonth())) {
            month = "全年 ";
        } else {
            month = String.format(Locale.CHINA, "%d月~%d月 ", countryTour.getOpenStartMonth(), countryTour.getOpenEndMonth());
        }
        if (countryTour.getOpenStartTime() == null || countryTour.getOpenEndTime() == null) {
            time = "";
        } else {
            time = String.format("%s~%s", format.format(countryTour.getOpenStartTime()), format.format(countryTour.getOpenEndTime()));
        }
        return (month + time).trim();
    }
}




