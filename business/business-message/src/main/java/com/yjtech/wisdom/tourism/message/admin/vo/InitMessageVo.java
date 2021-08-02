package com.yjtech.wisdom.tourism.message.admin.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yjtech.wisdom.tourism.command.entity.travelcomplaint.TravelComplaintEntity;
import com.yjtech.wisdom.tourism.common.enums.MessageEventTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 处理
 *
 * @author renguangqian
 * @date 2021/7/26 9:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class InitMessageVo implements Serializable {

    private static final long serialVersionUID = 6812211605569384396L;

    /**
     * 事件Id
     */
    @NotBlank
    private String eventId;

    /**
     * 事件名称
     */
    @NotBlank
    private String eventName;

    /**
     * 事件来源类型 0:旅游投诉 1:应急事件
     */
    private Byte eventType;

    /**
     * 事件业务类型 由各业务模块自行定义传输，暂定中文传输，只做前端展示
     */
    private String eventBusinessTypeText;

    /**
     * 事件状态 0:待指派 1:待处理 2:已处理
     */
    @NotNull
    private Integer eventStatus;

    /**
     * 事件发生日期/投诉时间
     */
    @NotBlank
    private String eventHappenDate;

    /**
     * 事件发生地址
     */
    @NotBlank
    private String eventHappenAddress;

    /**
     * 上报人/投诉人姓名
     */
    @NotBlank
    private String eventHappenPerson;

    /**
     * 上报人/投诉人 用户id
     */
    @NotNull
    private Long eventHappenPersonId;

    /**
     * 上报人/投诉人 联系电话
     */
    @NotBlank
    private String eventHappenPersonPhone;

    /**
     * 事件/投诉 处理人Id
     */
    @TableField(exist = false)
    private Long[] eventDealPersonIdArray;

    /**
     * 事件/投诉 处理人Id
     */
    private String eventDealPersonId;

    /**
     * 事件处理跳转的 URL
     */
    private String eventDealUrl;

    public void setEventDealPersonId(String eventDealPersonId) {
        String tmp = "";
        for (Long id : eventDealPersonIdArray) {
            tmp += id + ",";
        }
        this.eventDealPersonId = tmp;
    }

    public void buildTravelComplaintInfo(TravelComplaintEntity entity){
        setEventId(entity.getId().toString());
        setEventName(entity.getComplaintObject());
        setEventType(MessageEventTypeEnum.MESSAGE_EVENT_TYPE_TRAVEL_COMPLAINT.getValue());



    }


}
