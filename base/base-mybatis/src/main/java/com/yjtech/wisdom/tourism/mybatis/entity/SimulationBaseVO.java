package com.yjtech.wisdom.tourism.mybatis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 模拟数据 基础VO
 *
 * @date 2021/9/1 11:51
 * @author horadirm
 */
@Data
public class SimulationBaseVO implements Serializable {

    private static final long serialVersionUID = 5287769806205956927L;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime endTime;

    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    private Byte isSimulation = EntityConstants.DISABLED;

}
