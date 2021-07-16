package com.yjtech.wisdom.tourism.systemconfig.temp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 系统配置-大屏模板库
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_systemconfig_temp")
public class SystemconfigTempEntity extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * name 模板名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * img_url 模板缩略图
     */
    @TableField(value = "img_url")
    private String imgUrl;

    /**
     * temp_data 模板数据([0,1][15,16])
     */
    @TableField(value = "temp_data", typeHandler = JsonTypeHandler.class)
    private Integer[][] tempData;

}