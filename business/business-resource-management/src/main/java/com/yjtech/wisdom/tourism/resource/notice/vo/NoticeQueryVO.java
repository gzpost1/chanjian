package com.yjtech.wisdom.tourism.resource.notice.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

import java.util.List;

/**
 * 公告（通知）管理(TbNotice) 查询VO
 *
 * @author horadirm
 * @since 2022-07-07 14:48:45
 */
@Data
public class NoticeQueryVO extends PageQuery {
    private static final long serialVersionUID = 186545663985301169L;

    /**
     * 状态（0禁用 1启用）
     */
    private Byte status;

    /**
     * 名称
     */
    private String name;

    /**
     * 业务id列表
     */
    @JsonIgnore
    private List<String> businessIdList;


}