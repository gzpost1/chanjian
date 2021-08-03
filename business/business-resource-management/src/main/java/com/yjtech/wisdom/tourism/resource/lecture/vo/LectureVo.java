package com.yjtech.wisdom.tourism.resource.lecture.vo;

import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 展演讲座信息
 *
 * @author renguangqian
 * @date 2021/7/21 19:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LectureVo extends PageQuery implements Serializable {

    private static final long serialVersionUID = -40527196838634864L;

    /**
     * 场展演讲座名称
     */
    private String lectureName;

    /**
     * 场展演讲类型_通过字典管理配置
     */
    private String lectureType;

    /**
     * 关联场馆名称
     */
    private String venueId;

    /**
     * 开放日期-开始日期
     */
    private String holdStartDate;

    /**
     * 开放日期-结束日期
     */
    private String holdEndDate;

    /**
     * 联系电话，多个用“,”分割
     */
    private String phone;

    /**
     * 举办地点
     */
    private String holdAddress;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 封面图片Url
     */
    private String frontPicUrl;

    /**
     * 其他图片Url，多张用“,”逗号分割
     */
    private String otherPicUrl;

    /**
     * 简介
     */
    private String introduction;

    public void setHoldEndDate(String holdEndDate) {
        if (!StringUtils.isEmpty(holdEndDate)) {
            if ((DateTimeUtil.convertTimeToTimestamp(holdEndDate) < DateTimeUtil.convertTimeToTimestamp(holdStartDate))) {
                throw new CustomException("开始日期 不能大于 结束日期");
            }
        }
        this.holdEndDate = holdEndDate;
    }
}
