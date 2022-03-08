package com.yjtech.wisdom.tourism.bigscreen.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyFavoritesVo {
    /**
     * 名称
     */
    private String name;
    /**
     * 标签
     */
    private String label;
    /**
     * 发展方向
     */
    private String direction;
    /**
     * 建设要求
     */
    private String requirement;
    /**
     * 合作方式
     */
    private String cooperation;
    /**
     * 收藏时间
     */
    private LocalDateTime createTime;
    /**
     * 1.企业数据 2.项目数据
     */
    private Integer type;

}
