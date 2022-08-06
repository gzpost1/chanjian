package com.yjtech.wisdom.tourism.resource.talents.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.resource.talents.vo.TalentsPoolCreateVO;
import com.yjtech.wisdom.tourism.resource.talents.vo.TalentsPoolUpdateVO;
import lombok.Data;

import java.util.Date;

/**
 * 人才库管理(TbTalentsPool)实体类
 *
 * @author horadirm
 * @since 2022-08-06 09:37:15
 */
@Data
@TableName("tb_talents_pool")
public class TalentsPoolEntity extends BaseEntity {
    private static final long serialVersionUID = -481411068565228083L;

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 状态 0禁用 1启用
     */
    private Byte status;

    /**
     * 姓名
     */
    private String name;

    /**
     * 单位
     */
    private String organization;

    /**
     * 职位/职称
     */
    private String jobTitle;

    /**
     * 主攻领域
     */
    private String mainAreas;

    /**
     * 备注
     */
    private String remark;


    /**
     * 构建新增
     *
     * @param vo
     */
    public void buildCreate(TalentsPoolCreateVO vo){
        setId(IdWorker.getInstance().nextId());

        //默认启用
        setStatus(EntityConstants.ENABLED);
        setName(vo.getName());
        setOrganization(vo.getOrganization());
        setJobTitle(vo.getJobTitle());
        setMainAreas(vo.getMainAreas());
        setRemark(vo.getRemark());

        Date now = new Date();
        setCreateTime(now);
        setUpdateTime(now);
    }

    /**
     * 构建编辑
     *
     * @param vo
     */
    public void buildUpdate(TalentsPoolUpdateVO vo){
        if(StringUtils.isNotBlank(vo.getName())){
            setName(vo.getName());
        }
        if(StringUtils.isNotBlank(vo.getOrganization())){
            setOrganization(vo.getOrganization());
        }
        if(StringUtils.isNotBlank(vo.getJobTitle())){
            setJobTitle(vo.getJobTitle());
        }
        if(StringUtils.isNotBlank(vo.getMainAreas())){
            setMainAreas(vo.getMainAreas());
        }
        if(StringUtils.isNotBlank(vo.getRemark())){
            setRemark(vo.getRemark());
        }
    }

}