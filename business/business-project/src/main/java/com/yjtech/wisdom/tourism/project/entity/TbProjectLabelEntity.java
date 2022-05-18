package com.yjtech.wisdom.tourism.project.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.project.vo.ProjectLabelCreateVO;
import com.yjtech.wisdom.tourism.project.vo.ProjectLabelUpdateVO;
import lombok.Data;

/**
 * 项目标签(TbProjectLabel)实体类
 *
 * @author horadirm
 * @since 2022-05-18 17:16:29
 */
@Data
@TableName(value = "tb_project_label")
public class TbProjectLabelEntity extends BaseEntity {

    private static final long serialVersionUID = 4950832089037427178L;

    /**
     * 主键，序号
     */
    @TableId
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 状态（0-停用 1-启用）
     */
    private Byte status;


    /**
     * 构建新增
     * @param vo
     */
    public void buildCreate(ProjectLabelCreateVO vo){
        setName(vo.getName());
        //默认启用
        setStatus(EntityConstants.ENABLED);
    }

    /**
     * 构建编辑
     * @param vo
     */
    public void buildUpdate(ProjectLabelUpdateVO vo){
        if(StringUtils.isNotBlank(vo.getName())){
            setName(vo.getName());
        }
    }

}