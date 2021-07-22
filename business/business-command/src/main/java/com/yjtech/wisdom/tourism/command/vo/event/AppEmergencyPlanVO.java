package com.yjtech.wisdom.tourism.command.vo.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author xulei
 * @create 2021-07-19 17:13
 */
@Data
public class AppEmergencyPlanVO {
    /**
     * 预案名称
     */
    private String name;


    /**
     * 预案类型
     */
    private Long type;

    /**
     * 预案类型名称
     */
    private String typeName;

    /**
     * 预案发布机构
     */
    private String releaseInstitution;


    /**
     * 预案发布日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;


    /**
     * 预案发布内容
     */
    private String content;
}
