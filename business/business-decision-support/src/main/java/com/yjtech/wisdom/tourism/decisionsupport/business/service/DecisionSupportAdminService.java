package com.yjtech.wisdom.tourism.decisionsupport.business.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.DecisionDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.mapper.DecisionMapper;
import com.yjtech.wisdom.tourism.decisionsupport.business.vo.DecisionPageVo;
import com.yjtech.wisdom.tourism.decisionsupport.business.vo.DecisionVo;
import com.yjtech.wisdom.tourism.decisionsupport.business.vo.DeleteDecisionVo;
import org.springframework.stereotype.Service;

/**
 * 决策辅助-后台
 *
 * @author renguangqian
 * @date 2021/7/28 11:54
 */
@Service
public class DecisionSupportAdminService extends ServiceImpl<DecisionMapper, DecisionEntity> {

    /**
     * 查询决策_分页
     *
     * @param vo
     * @return
     */
    public IPage<DecisionDto> queryPageDecision(DecisionPageVo vo) {
        return baseMapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()),
                new LambdaQueryWrapper<DecisionEntity>()
                .like(!StringUtils.isEmpty(vo.getTargetName()), DecisionEntity::getTargetName, vo.getTargetName())
        ).convert(v -> JSONObject.parseObject(JSONObject.toJSONString(v), DecisionDto.class));
    }

    /**
     * 新增决策
     *
     * @param vo
     */
    public void insertDecision (DecisionVo vo) {
        DecisionEntity decisionEntity = JSONObject.parseObject(JSONObject.toJSONString(vo), DecisionEntity.class);
        try {
            baseMapper.insert(decisionEntity);
        } catch (Exception e) {
            // todo 捕获新增重复主键异常
            throw new CustomException("该指标已设定记录");
        }
    }

    /**
     * 删除决策
     *
     * @param vo
     */
    public void deleteDecision (DeleteDecisionVo vo) {
        baseMapper.deleteById(vo.getTargetId());
    }

    /**
     * 修改决策
     *
     * @param vo
     */
    public void updateDecision (DecisionVo vo) {
        DecisionEntity decisionEntity = JSONObject.parseObject(JSONObject.toJSONString(vo), DecisionEntity.class);
        baseMapper.updateById(decisionEntity);
    }
}
