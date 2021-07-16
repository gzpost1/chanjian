package com.yjtech.wisdom.tourism.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonLogicDeleteObj implements Serializable {
    private List<Long> ids;

    /**
     * 是否逻辑删除（0：否，1：是）
     */
    private Byte deleted = 1;
    private Long updateUser;
    private Date updateTime;
}
