package com.yjtech.wisdom.tourism.resource.video.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import lombok.Data;

/**
 * 大屏监控 查询VO
 *
 * @date 2021/8/19 15:35
 * @author horadirm
 */
@Data
public class ScreenVideoQueryVO extends TimeBaseQuery {

    private static final long serialVersionUID = 3223635917754668414L;

    /**
     * 状态(0:禁用,1:正常)
     */
    private Byte status;

    /**
     * 状态(0:离线,1:在线)
     */
    private Byte equipStatus;

}
