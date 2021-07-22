package com.yjtech.wisdom.tourism.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.mybatis.typehandler.ListObjectJsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liuhong
 * @date 2021-07-05 9:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_icon", autoResultMap = true)
public class Icon extends BaseEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 点位类型, 字典值, 来自字典表(闸机、停车场...)
     */
    private String type;

    /**
     * 点位类型名称, 创建、编辑不传
     */
    @TableField(exist = false)
    private String typeLabel;

    /**
     * 图标url
     */
    private String url;

}
