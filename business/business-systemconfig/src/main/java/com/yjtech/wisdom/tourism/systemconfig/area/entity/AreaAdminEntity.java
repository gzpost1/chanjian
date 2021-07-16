package com.yjtech.wisdom.tourism.systemconfig.area.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.MyBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 行政区域信息表
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_area_admin")
public class AreaAdminEntity extends MyBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 区域编码，主键
     */
    @TableId("code")
    private String code;


    /**
     * 行政编码
     */
    private String administration;


    /**
     * 简称
     */
    private String abbreviation;


    /**
     * 车牌首字母
     */
    private String plate;


    /**
     * 省级编码
     */
    private String provinceCode;


    /**
     * 市级编码
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String cityCode;





}
