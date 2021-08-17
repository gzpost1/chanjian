package com.yjtech.wisdom.tourism.resource.ticket.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.MyBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_ticket_check")
public class TicketCheckEntity extends MyBaseEntity {
    /**
     * 主键
     */
    @TableId(value = "check_id", type = IdType.ID_WORKER)
    private Long checkId;

    /**
     * 景区名称
     */
    private String scenicName;

    /**
     * 景区id
     */
    private Long scenicId;

    /**
     * 检票点
     */
    private String checkPlace;

    /**
     * 票号
     */
    private String ticketNo;

    /**
     * 检票时间
     */
    private Date checkTime;

    /**
     * 闸机
     */
    private String gateNo;

    /**
     * 检票数量
     */
    private Integer checkNum;
    /**
     * 游客类型
     */
    private String clientType;
}