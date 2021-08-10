package com.yjtech.wisdom.tourism.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 区划基础实体
 *
 * @Description 定义共用区划信息
 * @Author horadirm
 * @Date 2020/10/14 19:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AreaBaseEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 区域编码
     */
    @JsonIgnore
    @TableField(value = "area_code")
    private String areaCode;


}
