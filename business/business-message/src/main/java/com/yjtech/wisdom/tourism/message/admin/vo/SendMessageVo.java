package com.yjtech.wisdom.tourism.message.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 发送通知
 *
 * @author renguangqian
 * @date 2021/7/26 10:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SendMessageVo implements Serializable {

    private static final long serialVersionUID = -1282621254136117931L;

    /**
     * 发送类型 0:后台 1:App 2:短信
     */
    private Integer[] sendType;

    /**
     * 事件id  后台类型-必传
     */
    private Long eventId;

    /**
     * 事件类型
     */
    private Integer eventType;

    /**
     * app:标题
     */
    private String title;

    /**
     * app:内容
     */
    private String content;

    /**
     * 管理后台
     */
    private String pcTitle;

    /**
     * 短信模板内容
     */
    private List<String> smsContent;

    /**
     * 事件处理人Id
     */
    private Long[] eventDealPersonIdArray;

}
