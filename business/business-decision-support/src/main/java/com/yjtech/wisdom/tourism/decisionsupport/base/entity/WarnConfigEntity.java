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
 * 预警配置项
 *
 * @author renguangqian
 * @date 2021/7/27 14:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_decision_warn_config")
public class WarnConfigEntity implements Serializable {

    private static final long serialVersionUID = 1088199785188573741L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 预警配置项名称
     */
    private String configName;

    /**
     * 指标id
     */
    private Long targetId;

    /**
     * 配置项类型 0:文本  1：数值
     */
    private Integer configType;

    /**
     * 预警配置项 话术key
     */
    private String configKey;

}
