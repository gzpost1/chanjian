package com.yjtech.wisdom.tourism.resource.depot.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.*;

/**
 * (TbDepot)实体类
 *
 * @author zc
 * @since 2021-07-04 11:39:24
 */
@Data
@TableName("tb_depot")
public class DepotEntity extends BaseEntity {

    /**
    * 停车场id
    */
    @TableId(type=IdType.ID_WORKER)
    private Long depotId;
    /**
    * 停车场名称
    */
    private String name;
    /**
     * 设备编号
     */
    private String deviceId;
    /**
    * 经度
    */
    private String longitude;
    /**
    * 维度
    */
    private String latitude;
    /**
    * 景点id
    */
    private Long scenicSpotId;
    /**
    * 地址
    */
    private String address;
    /**
    * 停车位
    */
    private Integer spaceTotal;

    /**
     *  启用/停用
     * */
    private Byte status;

    /**
     *  设备状态(1:在线, 0:离线)
     * */
    private Byte equipStatus;

    /**
     * 第三方外部主键
     */
    private String externalId;

    /**
     * 已使用车位
     */
    private Integer spaceUsed;

    /**
     * 停车库车位剩余数
     */
    private Integer spaceSurplus;

    /**
     * 预警使用率
     */
    private Float warnUsedRate;

    /**
     * 报警使用率
     */
    private Float alarmUsedRate;
}