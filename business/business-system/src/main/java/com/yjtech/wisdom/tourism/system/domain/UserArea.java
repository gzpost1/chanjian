package com.yjtech.wisdom.tourism.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.MyBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author songjun
 * @since 2023/9/7
 */
/**
    * 用户和区域关联表
    */
@Data
@TableName(value = "tb_user_area")
public class UserArea {
    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 区域编码
     */
    @TableId(value = "area_code", type = IdType.AUTO)
    private String areaCode;
}