package com.yjtech.wisdom.tourism.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JsonTypeHandler;
import com.yjtech.wisdom.tourism.system.vo.TagUpdateVO;
import io.jsonwebtoken.lang.Collections;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 标签管理(TbTag)实体类
 *
 * @author horadirm
 * @since 2022-03-11 09:59:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@TableName(value = "tb_tag", autoResultMap = true)
public class TagEntity extends BaseEntity {

    private static final long serialVersionUID = 671063174105380239L;

    /**
     * id
     */
    @TableId("id")
    private Long id;

    /**
     * 状态(0:禁用,1:正常)
     */
    private Byte status;

    /**
     * 企业角色
     */
    private String enterpriseRole;

    /**
     * 标签信息
     */
    @TableField(value = "tag_info", typeHandler = JsonTypeHandler.class)
    private List<String> tagInfo;


    /**
     * 构建编辑
     * @param vo
     */
    public void buildUpdate(TagUpdateVO vo){
        if(!Collections.isEmpty(vo.getTagInfo())){
            setTagInfo(vo.getTagInfo());
        }
    }

}