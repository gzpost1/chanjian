package com.yjtech.wisdom.tourism.message.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.annotation.HandlesTypes;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 消息中心
 *
 * @author renguangqian
 * @date 2021/7/23 10:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_message_center")
public class MessageEntity implements Serializable {

    private static final long serialVersionUID = 3063663633871165100L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 事件Id
     */
    private Long eventId;

    /**
     * 事件类型 0:旅游投诉 1:应急事件
     */
    private Integer eventType;

    /**
     * 事件/投诉 处理人Id
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    private List<Long> eventDealPersonId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date updateTime;

    /**
     * 创建人ID
     */
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改人ID
     */
    @JsonIgnore
    @TableField(fill = FieldFill.UPDATE)
    private Long updateUser;

    /**
     * 是否删除, 0:否, 1:是
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    @JsonIgnore
    private Byte deleted;

}
