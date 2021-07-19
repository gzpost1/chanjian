package com.yjtech.wisdom.tourism.command.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 应急预案-类型管理
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_emergency_plan_type")
public class EmergencyPlanTypeEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("id")
    private Long id;


    /**
     * 名称
     */
    private String name;



}
