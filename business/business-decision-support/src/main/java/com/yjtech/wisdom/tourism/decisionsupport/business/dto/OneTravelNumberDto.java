package com.yjtech.wisdom.tourism.decisionsupport.business.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 一码游投诉量
 *
 * @author renguangqian
 * @date 2021/8/9 17:59
 */
@Data
public class OneTravelNumberDto implements Serializable {

    private static final long serialVersionUID = 7846712143726571936L;

    /**
     * 年月
     */
    private String time;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 同比
     */
    private String tb;

    /**
     * 环比
     */
    private String hb;

    @JSONField(name = "same")
    public void setTb(String tb) {
        this.tb = tb;
    }

    @JSONField(name = "sequential")
    public void setHb(String hb) {
        this.hb = hb;
    }
}
