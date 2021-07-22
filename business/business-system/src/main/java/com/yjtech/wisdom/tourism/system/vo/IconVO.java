package com.yjtech.wisdom.tourism.system.vo;

import com.yjtech.wisdom.tourism.common.core.domain.validate.CreateGroup;
import com.yjtech.wisdom.tourism.common.core.domain.validate.UpdateGroup;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.system.domain.IconDetail;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liuhong
 * @date 2021-07-05 9:06
 */
@Data
public class IconVO extends BaseEntity {
    /**
     * 主键
     */
    @NotNull(message = "id不能为空", groups = {UpdateGroup.class})
    private Long id;

    /**
     * 点位类型, 来自字典表(闸机、停车场...)
     */
    @NotBlank(message = "点位类型不能为空", groups = {UpdateGroup.class, CreateGroup.class})
    private String type;

    /**
     * 点位类型名称, 创建、编辑不传
     */
    private String typeLabel;

    /**
     * 图标明细
     */
    private List<IconDetail> value;
}
