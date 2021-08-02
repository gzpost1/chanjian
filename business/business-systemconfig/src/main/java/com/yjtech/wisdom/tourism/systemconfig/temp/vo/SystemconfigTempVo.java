package com.yjtech.wisdom.tourism.systemconfig.temp.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 系统配置-大屏模板库
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemconfigTempVo {
    /**
     * id
     */
    private Long id;

    /**
     * name 模板名称
     */
    private String name;

    /**
     * img_url 模板缩略图
     */
    private String imgUrl;


    /**
     * create_time 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * temp_data 模板数据([0,1][15,16])
     */
    @TableField(value = "temp_data",typeHandler = JsonTypeHandler.class)
    private Integer[][] tempData;

    private String layoutJson;
}