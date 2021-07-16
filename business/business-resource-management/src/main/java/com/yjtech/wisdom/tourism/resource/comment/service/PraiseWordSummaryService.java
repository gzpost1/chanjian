package com.yjtech.wisdom.tourism.resource.comment.service;

import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.resource.comment.entity.PraiseWordSummaryEntity;
import com.yjtech.wisdom.tourism.resource.comment.mapper.PraiseWordSummaryMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PraiseWordSummaryService extends ServiceImpl<PraiseWordSummaryMapper, PraiseWordSummaryEntity> {

    public Map<String,Object> queryHotWordBYComments() {
        Map<String,Object> map = new LinkedHashMap<>();
        List<BaseVO> hotWords = new ArrayList<>();
        List<BaseVO> evaluations = new ArrayList<>();

        map.put("hotWords",hotWords);
        map.put("evaluations",evaluations);

        List<PraiseWordSummaryEntity> list = this.baseMapper.queryHotWordBYComments();
        if(CollectionUtils.isNotEmpty(list)){
            Map<Byte, List<PraiseWordSummaryEntity>> collect =
                    list.stream().collect(Collectors.groupingBy(PraiseWordSummaryEntity::getType));

            List<PraiseWordSummaryEntity> oldHotWords = collect.get(Byte.valueOf("1"));
            if(CollectionUtils.isNotEmpty(oldHotWords)){
                oldHotWords = oldHotWords.stream().sorted(Comparator.comparing(PraiseWordSummaryEntity::getResult)).collect(Collectors.toList());
                hotWords = oldHotWords.stream().map(e -> new BaseVO(e.getName(),e.getResult())).collect(Collectors.toList());
                map.put("hotWords",hotWords);

            }

            List<PraiseWordSummaryEntity> oldevaluations = collect.get(Byte.valueOf("2"));
            if(CollectionUtils.isNotEmpty(oldevaluations)){
                oldevaluations = oldevaluations.stream().sorted(Comparator.comparing(PraiseWordSummaryEntity::getResult)).collect(Collectors.toList());
                evaluations = oldevaluations.stream().map(e -> new BaseVO(e.getName(),e.getResult())).collect(Collectors.toList());
                map.put("evaluations",evaluations);
            }
        }
        return map;
    }
}
