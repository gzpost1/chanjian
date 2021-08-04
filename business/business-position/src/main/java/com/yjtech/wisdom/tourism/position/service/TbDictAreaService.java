package com.yjtech.wisdom.tourism.position.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisServiceImpl;
import com.yjtech.wisdom.tourism.position.domain.DictAreaQuery;
import com.yjtech.wisdom.tourism.position.domain.DictAreaTree;
import com.yjtech.wisdom.tourism.position.entity.TbDictAreaEntity;
import com.yjtech.wisdom.tourism.position.mapper.TbDictAreaMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域信息表 服务实现类
 *
 * @author Mujun
 * @since 2020-10-27
 */
@Service
public class TbDictAreaService extends BaseMybatisServiceImpl<TbDictAreaMapper, TbDictAreaEntity>  {
    public List<DictAreaTree> getDictAreaTree(String code, Integer level) {
        List<DictAreaTree> dictAreaTrees = getDictAreaTreeInner(code, null, 0, level);
        return dictAreaTrees;
    }

    public List<TbDictAreaEntity> queryDictAreaList(DictAreaQuery query) {
        LambdaQueryWrapper<TbDictAreaEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StringUtils.isNotBlank(query.getCode()), TbDictAreaEntity::getCode, query.getCode());
        wrapper.eq(StringUtils.isNotBlank(query.getProvinceCode()), TbDictAreaEntity::getProvinceCode,
                query.getProvinceCode());
        wrapper.ne(TbDictAreaEntity::getName, "市辖区");
        wrapper.like(StringUtils.isNotBlank(query.getName()), TbDictAreaEntity::getName, query.getName());
        if(query.getLevelFilterList() != null && query.getLevelFilterList().size() > 0) {
            wrapper.in(TbDictAreaEntity::getLevel, query.getLevelFilterList());
        }
        wrapper.orderByDesc(StringUtils.isNotBlank(query.getName()),TbDictAreaEntity::getName);
//        SqlHelper.getLikeOrder(StringUtils.isNotBlank(query.getName()), wrapper, TbDictAreaEntity::getName);
        return baseMapper.selectList(wrapper);
    }

    private List<DictAreaTree> getDictAreaTreeInner(String code, DictAreaTree parent, Integer curLevel,
                                                    Integer maxLevel) {
        List<DictAreaTree> dictAreaTrees = new ArrayList<>();
        if (curLevel < maxLevel) {
            LambdaQueryWrapper<TbDictAreaEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(TbDictAreaEntity::getParentCode, code);
            wrapper.ne(TbDictAreaEntity::getName, "市辖区");
            List<TbDictAreaEntity> dictAreaEntityList = baseMapper.selectList(wrapper);
            for (TbDictAreaEntity dictAreaEntity : dictAreaEntityList) {
                DictAreaTree dictAreaTree = DictAreaTree.builder()
                        .data(dictAreaEntity)
                        .build();
                List<DictAreaTree> dictAreaTreesChild = getDictAreaTreeInner(dictAreaEntity.getCode(), dictAreaTree,
                        curLevel + 1, maxLevel);
                if (dictAreaTreesChild != null) {
                    dictAreaTree.setChild(dictAreaTreesChild);
                }
                dictAreaTrees.add(dictAreaTree);
            }
        } else {
            return null;
        }
        return dictAreaTrees;
    }

    static public Integer getAreaLevel(TbDictAreaEntity dictAreaEntity) {
        return dictAreaEntity.getLevel();
    }
}
