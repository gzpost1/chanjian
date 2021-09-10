package com.yjtech.wisdom.tourism.resource.venue.vo;

import com.yjtech.wisdom.tourism.common.validator.EnumValue;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 更新
 *
 * @author renguangqian
 * @date 2021/9/8 15:43
 */
@Data
public class UpdateVO implements Serializable {
    @NotBlank(message = "id不能为空")
    private String id;

    @EnumValue(values = {"1", "0"},message = "状态必须为1或0")
    private Byte status;
}
