package com.yjtech.wisdom.tourism.chat.dto;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author han
 * @createTime 2022/5/12 14:16
 * @description 发言记录查询实体
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MessageRecordQuery extends PageQuery {

    private Long id;

    private List<Long> ids;
    /**
     * 发送人
     */
    private Long fromUserId;

    private List<Long> fromUserIdList;

    /**
     *  接收人
     */
    private Long toUserId;

    /**
     * 查询数据条数限制
     */
    private DataLimit dataLimit;

    /**
     * 起始时间
     */
    private Date startTime;

    /**
     *  结束时间
     */
    private Date endTime;

}
