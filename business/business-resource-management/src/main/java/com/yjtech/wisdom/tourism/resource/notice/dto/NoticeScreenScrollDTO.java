package com.yjtech.wisdom.tourism.resource.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告（通知）管理(TbNotice) 大屏滚动条 返回DTO
 *
 * @author horadirm
 * @since 2022-07-07 14:48:45
 */
@Data
public class NoticeScreenScrollDTO implements Serializable {
    private static final long serialVersionUID = -4612250447524513026L;

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 详情内容
     */
    private String content;

    /**
     * 消息类型（0-公告 1-项目申报通知）
     * 字典类型：notice_type
     */
    private Byte type;

    /**
     * 业务id
     */
    private String businessId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}