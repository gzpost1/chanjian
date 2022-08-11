package com.yjtech.wisdom.tourism.common.bean.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 项目数据统计 DTO
 *
 * @date 2022/8/5 16:51
 * @author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDataStatisticsDTO implements Serializable {
    private static final long serialVersionUID = 242782155428517041L;

    /**
     * 浏览数
     */
    private Integer viewNum;

    /**
     * 点赞数
     */
    private Integer likeNum;

    /**
     * 留言数
     */
    private Integer messageNum;

    /**
     * 收藏数
     */
    private Integer collectNum;

}
