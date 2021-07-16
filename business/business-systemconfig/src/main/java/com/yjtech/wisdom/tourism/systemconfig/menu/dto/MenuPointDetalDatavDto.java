package com.yjtech.wisdom.tourism.systemconfig.menu.dto;

import com.yjtech.wisdom.tourism.system.domain.IconDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李波
 * @description: 菜单数据-点位详细
 * @date 2021/7/416:39
 */
@Data
public class MenuPointDetalDatavDto {
    /**
     * 点位类型
     */
    private String pointType;

    /**
     * 点位类型名称
     */
    private String pointName;

    /**
     * 是否展示 0否 1是
     */
    private Byte isShow;

    /**
     * 地图点位后端请求url
     */
    private String serviceUrl;

    /**
     * 图标明细
     */
    private List<IconDetail> value = new ArrayList<>();
}
