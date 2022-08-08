package com.yjtech.wisdom.tourism.resource.industry.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.mybatis.typehandler.ListObjectJsonTypeHandler;
import com.yjtech.wisdom.tourism.resource.industry.vo.IndustryFundsCreateVO;
import com.yjtech.wisdom.tourism.resource.industry.vo.IndustryFundsUpdateVO;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 产业资金管理(TbIndustryFunds)实体类
 *
 * @author horadirm
 * @since 2022-08-06 10:13:36
 */
@Data
@TableName("tb_industry_funds")
public class IndustryFundsEntity extends BaseEntity {
    private static final long serialVersionUID = -4381369802603798798L;

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
     * 企业名称
     */
    private String companyName;

    /**
     * 主营行业
     */
    private String mainIndustry;

    /**
     * 投资总金额 单位：亿元
     */
    private BigDecimal totalInvestmentSum;

    /**
     * 项目情况
     */
    @TableField(typeHandler = ListObjectJsonTypeHandler.class)
    private List<ProjectInfo> projectInfo;


    /**
     * 构建新增
     *
     * @param vo
     */
    public void buildCreate(IndustryFundsCreateVO vo){
        setId(IdWorker.getInstance().nextId());

        //默认启用
        setStatus(EntityConstants.ENABLED);
        setCompanyName(vo.getCompanyName());
        setMainIndustry(vo.getMainIndustry());
        //计算投资总金额
        setTotalInvestmentSum(new BigDecimal(vo.getProjectInfo().stream().mapToDouble(item -> item.getInvestmentSum().doubleValue()).sum()));
        setProjectInfo(vo.getProjectInfo());

        Date now = new Date();
        setCreateTime(now);
        setUpdateTime(now);
    }

    /**
     * 构建编辑
     *
     * @param vo
     */
    public void buildUpdate(IndustryFundsUpdateVO vo){
        if(StringUtils.isNotBlank(vo.getCompanyName())){
            setCompanyName(vo.getCompanyName());
        }
        if(StringUtils.isNotBlank(vo.getMainIndustry())){
            setMainIndustry(vo.getMainIndustry());
        }
        if(CollectionUtils.isNotEmpty(vo.getProjectInfo())){
            setProjectInfo(vo.getProjectInfo());
            setTotalInvestmentSum(new BigDecimal(vo.getProjectInfo().stream().mapToDouble(item -> item.getInvestmentSum().doubleValue()).sum()));
        }
    }

}