package com.yjtech.wisdom.tourism.resource.notice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.enums.NoticeTypeEnum;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.resource.notice.vo.NoticeCreateVO;
import com.yjtech.wisdom.tourism.resource.notice.vo.NoticeUpdateVO;
import lombok.Data;

/**
 * 公告（通知）管理(TbNotice)实体类
 *
 * @author horadirm
 * @since 2022-07-07 14:48:45
 */
@Data
@TableName("tb_notice")
public class NoticeEntity extends BaseEntity {
    private static final long serialVersionUID = 943065952349713474L;

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 状态（0禁用 1启用）
     */
    private Byte status;

    /**
     * 状态描述
     */
    @TableField(exist = false)
    private String statusDesc;

    /**
     * 名称
     */
    private String name;

    /**
     * 详情内容
     */
    private String content;

    /**
     * 消息类型（0-公告 1-项目申报通知）
     * 字典类型：notice_type
     */
    private Byte type;

    /**
     * 业务id
     */
    private String businessId;

    /**
     * 读取标识（0未读 1已读）
     */
    private Byte readFlag;


    /**
     * 构建新增
     *
     * @param vo
     */
    public void buildCreate(NoticeCreateVO vo){
        setId(IdWorker.getInstance().nextId());

        //默认启用
        setStatus(EntityConstants.ENABLED);
        setName(vo.getName());
        setContent(vo.getContent());
        //默认类型：公告
        setType(null == vo.getType() ? NoticeTypeEnum.NOTICE_TYPE_PUBLIC.getType() : vo.getType());
        //默认空
        setBusinessId(vo.getBusinessId());
        //默认未读
        setReadFlag(EntityConstants.DISABLED);
    }

    /**
     * 构建编辑
     *
     * @param vo
     */
    public void buildUpdate(NoticeUpdateVO vo){
        if(StringUtils.isNotBlank(vo.getName())){
            setName(vo.getName());
        }
        if(StringUtils.isNotBlank(vo.getContent())){
            setContent(vo.getContent());
        }
    }

}