package com.yjtech.wisdom.tourism.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuyongchong
 * @since 2022-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("chat_record")
public class ChatRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 发起人
     */
    private Long initiatorId;

    /**
     * 会话人
     */
    private Long sessionId;

    /**
     * Y-已删，N-未删
     */
    private String logDel;

    /**
     * 删除时间
     */
    private Date delTime;

    /**
     * 最新聊天时间
     */
    private Date lastChatTime;

    private String hasUnread;

}
