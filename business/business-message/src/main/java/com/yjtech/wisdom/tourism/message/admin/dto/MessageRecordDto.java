package com.yjtech.wisdom.tourism.message.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 查询是否存在新的消息记录
 *
 * @author renguangqian
 * @date 2021/7/29 15:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MessageRecordDto implements Serializable {

    private static final long serialVersionUID = -5056366628458408289L;

    /**
     * 是否新增报警记录
     */
    private boolean isAdd;

    /**
     * 新增的数目
     */
    private Long addNumber;

}
