package com.yjtech.wisdom.tourism.message.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息记录
 *
 * @author renguangqian
 * @date 2021/7/26 19:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_message_record")
public class MessageRecordEntity implements Serializable {

    private static final long serialVersionUID = 5736038181740541370L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送方式 0:后台 1:App 2:短信
     */
    private Integer sendType;

    /**
     * 发送对象
     */
    private String sendObject;

    /**
     * 事件id
     */
    private Long eventId;

    /**
     * 是否发送成功 0：失败 1：成功
     */
    private Byte success;

    /**
     * 返回消息
     */
    private String response;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 是否删除, 0:否, 1:是
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    @JsonIgnore
    private Byte deleted;
}
