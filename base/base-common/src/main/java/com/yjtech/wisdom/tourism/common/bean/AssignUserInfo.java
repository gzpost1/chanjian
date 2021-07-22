package com.yjtech.wisdom.tourism.common.bean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 指派用户信息
 *
 * @Date 2021/7/22 12:03
 * @Author horadirm
 */
@Data
public class AssignUserInfo implements Serializable {

    private static final long serialVersionUID = -7492836732096482229L;

    /**
     * 指派用户id列表
     */
    @NotEmpty(message = "指派用户id列表不能为空")
    private List<Long> assignUserIdList;

    /**
     * 是否平台通知
     */
    @NotNull(message = "平台通知标识不能为空")
    private Boolean platformNoticeFlag;

}
