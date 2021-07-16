package com.yjtech.wisdom.tourism.resource.hydrological.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.*;

import java.math.BigDecimal;

/**
 * <p>
 * 水文信息监测设备
 * </p>
 *
 * @author wuyongchong
 * @since 2020-09-16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tb_hydrological_device")
public class HydrologicalDevice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 设备id
     */
    private Long id;

    /**
     * 设备编号
     */
    private String sn;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 维度
     */
    private String latitude;

    /**
     * 预警水位
     */
    private BigDecimal warningWaterLevel;

    /**
     * 报警水位
     */
    private BigDecimal alarmWaterLevel;

    /**
     * 报警类型, 来自字典表
     */
    private String alarmMode;

    /**
     *  启停用(0:停用,1:启用)
     * */
    private Byte status;

    /**
     *  设备状态(0:离线, 1:在线, 2:预警, 3:报警)
     * */
    private Byte equipStatus;

    /**
     * 状态图标url
     */
    @TableField(exist = false)
    private String iconUrl;
}
