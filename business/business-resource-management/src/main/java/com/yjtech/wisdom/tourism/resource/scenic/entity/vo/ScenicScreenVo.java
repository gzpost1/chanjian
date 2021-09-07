package com.yjtech.wisdom.tourism.resource.scenic.entity.vo;

import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.weather.vo.WeatherInfoVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

import static com.yjtech.wisdom.tourism.common.utils.StringUtils.isNull;

/**
 * 大屏景区分布分页vo
 */
@Data
public class ScenicScreenVo {

    /**
     * id
     */
    private Long id;

    /**
     * 景区名称
     */
    private String name;

    /**
     * 景区等级
     */
    private String level;

    /**
     * 地址
     */
    private String address;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 今日入园数
     */
    private Integer enterNum;

    /**
     * 地图缩放比例
     */
    private Integer mapZoomRate;

    /**
     * 开放日期-开始日期
     */
    private String openStartDate;

    /**
     * 开放日期-结束日期
     */
    private String openEndDate;

    /**
     * 开放日期-开始时间
     */
    private String openStartTime;

    /**
     * 开放日期-结束时间
     */
    private String openEndTime;

    /**开放时间*/
    private String openTime;

    /**
     * 景区承载量
     */
    private Integer bearCapacity;

    /**
     * 舒适度预警比例
     */
    private BigDecimal comfortWarnRate;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 应急联系人
     */
    private String emergencyContact;

    /**
     * 应急联系人电话
     */
    private String emergencyContactPhone;

    /**
     * 封面图片Url
     */
    private String frontPicUrl;

    /**
     * 其他图片Url，多张用“,”逗号分割
     */
    private List<String> otherPicUrl;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 启停用状态
     */
    private Byte status;

    /**
     * 舒适类别 （舒适、拥挤）
     */
    private String comfortCategory;

    /**
     * 景区状态(数据字典)
     */
    private Byte equipStatus;

    /**
     * 图标地址
     */
    private String iconUrl;

    /**
     * 天气
     */
    private WeatherInfoVO weatherInfoVO;

    public void setEnterNum(Integer enterNum) {
        this.enterNum = isNull(enterNum) ? 0 : enterNum;
    }

    public String getComfortCategory() {
        String comfort = "舒适";
        if (!isNull(this.comfortWarnRate)) {
            comfort = (this.enterNum / this.bearCapacity) >= this.comfortWarnRate.intValue() ? "拥挤" : "舒适";
        }
        return comfort;
    }

    public String getOpenTime() {
        StringBuilder openTime = new StringBuilder();
        if(StringUtils.isNotBlank(this.openStartDate) && StringUtils.isNotBlank(this.openEndDate)){
            if(this.openStartDate.equals("1") && this.openEndDate.equals("12")){
                openTime.append("全年 ");
            }else {
                openTime.append(this.openStartDate + "月~" + this.openEndDate + "月 ");
            }
        }
        if(StringUtils.isNotBlank(this.openStartTime) && StringUtils.isNotBlank(this.openEndTime)){
            openTime.append(this.openStartTime + "-" + this.openEndTime);
        }
        return openTime.toString();
    }
}
