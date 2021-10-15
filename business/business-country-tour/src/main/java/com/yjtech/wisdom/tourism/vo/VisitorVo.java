package com.yjtech.wisdom.tourism.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 游客拜访数据
 *
 * @author renguangqian
 * @date 2021/7/22 17:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorVo extends TimeBaseQuery implements Serializable {

    private static final long serialVersionUID = 5294322588561817208L;

    /**
     * 省市区域编号
     */
    private String adcode;

    /**
     * 统计类型:0,统计总人数 1.年龄 2.性别 5.消费能力 6.学历比例 7 有车比例 8.健身比例 9.是否理财  11.到访常客比例 12.旅游距离,13.旅游目的地国内偏好,14.旅游目的地国外偏好,15人生阶段 16美食偏好 17购物偏好
     * 18娱乐偏好 19酒店偏好 31.全国top,32.全省top,33.省外市级top 41.月度统计人数,51,统计省内人数
     */
    @NotBlank
    @Min(value = 31)
    @Max(value = 32)
    private String statisticsType;

    /**
     * top排名数,如果输入5就显示top5
     */
    private Integer limit;

    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    private Byte isSimulation = 0;

    /**
     * 开始时间
     */
    private String beginDate;

    /**
     * 结束时间
     */
    private String endDate;
}
