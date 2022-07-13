package com.yjtech.wisdom.tourism.resource.introduction.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.resource.introduction.vo.PlatformIntroductionCreateVO;
import com.yjtech.wisdom.tourism.resource.introduction.vo.PlatformIntroductionUpdateVO;
import lombok.Data;

/**
 * 平台介绍(TbPlatformIntroduction)实体类
 *
 * @author horadirm
 * @since 2022-07-07 13:51:15
 */
@Data
@TableName(value = "tb_platform_introduction")
public class PlatformIntroductionEntity extends BaseEntity {
    private static final long serialVersionUID = 454481722136276316L;

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
     * 名称
     */
    private String name;

    /**
     * 封面图片
     */
    private String coverUrl;

    /**
     * 详情内容
     */
    private String content;


    /**
     * 构建新增
     *
     * @param vo
     */
    public void buildCreate(PlatformIntroductionCreateVO vo){
        setId(IdWorker.getInstance().nextId());

        setName(vo.getName());
        setCoverUrl(vo.getCoverUrl());
        setContent(vo.getContent());
        //默认启用
        setStatus(EntityConstants.ENABLED);
    }

    /**
     * 构建编辑
     *
     * @param vo
     */
    public void buildUpdate(PlatformIntroductionUpdateVO vo){
        if(StringUtils.isNotBlank(vo.getName())){
            setName(vo.getName());
        }
        if(StringUtils.isNotBlank(vo.getCoverUrl())){
            setCoverUrl(vo.getCoverUrl());
        }
        if(StringUtils.isNotBlank(vo.getContent())){
            setContent(vo.getContent());
        }
    }

}