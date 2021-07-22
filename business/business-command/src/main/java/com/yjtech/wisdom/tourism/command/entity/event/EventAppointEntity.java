package com.yjtech.wisdom.tourism.command.entity.event;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 配置人员表
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_event_appoint",autoResultMap = true)
public class EventAppointEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("id")
    private Long id;


    /**
     * 指派人员
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    private List<String> appointPersonnel;


    /**
     * 消息通知
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    private List<String> notice;



}
