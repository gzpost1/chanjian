package com.yjtech.wisdom.tourism.decisionsupport.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 指标库
 *
 * @author renguangqian
 * @date 2021/7/27 14:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_decision_target_library")
public class TargetLibraryEntity implements Serializable {

    private static final long serialVersionUID = -2406705988110940387L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 指标名称
     */
    private String name;

}
