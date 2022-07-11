package com.yjtech.wisdom.tourism.resource.notice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 公告（通知）管理(TbNotice) 创建VO
 *
 * @author horadirm
 * @since 2022-07-07 14:48:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeCreateVO implements Serializable {
    private static final long serialVersionUID = -2777839741789034226L;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    @Size(min = 1, max = 30, message = "名称长度不合法")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]*$", message = "名称非法，仅支持中文")
    private String name;

    /**
     * 消息类型（0-公告 1-项目申报通知）
     * 字典类型：notice_type
     */
    private Byte type;

    /**
     * 详情内容
     */
    private String content;

    /**
     * 业务id
     */
    private String businessId;


}