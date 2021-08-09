package com.yjtech.wisdom.tourism.hotel.dto;

import com.yjtech.wisdom.tourism.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Author Mujun~
 * @Date 2020-10-26 15:23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToiletBean implements Serializable {
    @Excel(name= "厕所编号")
    private String num;
    @Excel(name= "项目名称")
    private String projectName;
    @Excel(name= "项目类别")
    private String projectType;
    @Excel(name= "业主单位")
    private String company;
    @Excel(name= "建设性质")
    private String nature;
    @Excel(name= "拟建等级")
    private String lev;


}
