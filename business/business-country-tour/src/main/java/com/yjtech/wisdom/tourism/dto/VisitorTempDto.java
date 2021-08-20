package com.yjtech.wisdom.tourism.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 游客来源中转
 *
 * @author renguangqian
 * @date 2021/8/20 13:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class VisitorTempDto implements Serializable {
    private static final long serialVersionUID = 6170262004024665541L;
    /**
     * 省市区域编号
     */
    private String adcode;

    /**
     * 统计类型:0,统计总人数 1.年龄 2.性别 5.消费能力 6.学历比例 7 有车比例 8.健身比例 9.是否理财  11.到访常客比例 12.旅游距离,13.旅游目的地国内偏好,14.旅游目的地国外偏好,15人生阶段 16美食偏好 17购物偏好
     * 18娱乐偏好 19酒店偏好 31.全国top,32.全省top,33.省外市级top 41.月度统计人数,51,统计省内人数
     */
    private String type;

    /**
     * top排名数,如果输入5就显示top5
     */
    private Integer limit;

    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    private Integer isSimulation = 0;

    /**
     * 开始时间
     */
    private String beginDate;

    /**
     * 结束时间
     */
    private String endDate;
    /**
     * 当前页
     * */
    private Long pageNo;

    /**
     *  每页大小
     *  */
    private Long pageSize;

    @JSONField(name = "statisticsType")
    public void setType(String type) {
        this.type = type;
    }
}
