package com.yjtech.wisdom.tourism.project.service;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.project.dto.ProjectQuery;
import com.yjtech.wisdom.tourism.project.entity.TbProjectInfoEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectLabelRelationEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectResourceEntity;
import com.yjtech.wisdom.tourism.project.mapper.TbProjectInfoMapper;
import com.yjtech.wisdom.tourism.project.vo.ProjectAmountVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TbProjectInfoService extends ServiceImpl<TbProjectInfoMapper, TbProjectInfoEntity> {

    @Autowired
    private TbProjectResourceService projectResourceService;
    @Autowired
    private TbProjectLabelRelationService tbProjectLabelRelationService;

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        this.removeById(id);

        LambdaUpdateWrapper<TbProjectResourceEntity> wrapper = new LambdaUpdateWrapper<TbProjectResourceEntity>();
        wrapper.eq(TbProjectResourceEntity::getProjectId,id);
        boolean result = projectResourceService.remove(wrapper);

        //同步删除该项目的标签关联
        if(result){
            tbProjectLabelRelationService.remove(new LambdaQueryWrapper<TbProjectLabelRelationEntity>()
                    .eq(TbProjectLabelRelationEntity::getProjectId, id));
        }
    }


    /**
     * 查询列表
     *
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public List<TbProjectInfoEntity> queryForList(ProjectQuery params){
        List<TbProjectInfoEntity> list = baseMapper.queryForList(null, params);
        //构建已选中项目标签id列表
        if(CollectionUtils.isNotEmpty(list)){
            for(TbProjectInfoEntity entity : list){
                entity.setPitchOnLabelIdList(tbProjectLabelRelationService.queryForLabelIdListByProjectId(entity.getId()));
            }
        }
        return list;
    }

    /**
     * 查询分页
     *
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public IPage<TbProjectInfoEntity> queryForPage(ProjectQuery params){
        Page<TbProjectInfoEntity> page = new Page<>(params.getPageNo(), params.getPageSize());
        List<TbProjectInfoEntity> records = baseMapper.queryForList(page, params);
        //构建已选中项目标签id列表
        if(CollectionUtils.isNotEmpty(records)){
            for(TbProjectInfoEntity entity : records){
                entity.setPitchOnLabelIdList(tbProjectLabelRelationService.queryForLabelIdListByProjectId(entity.getId()));
            }
            page.setRecords(records);
        }
        page.setRecords(records);
        return page;
    }

    /**
     * 大屏-数据统计-平台项目累计总数
     * @Param:
     * @return:
     */
    public List<BaseValueVO> queryProjectNumTrend() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime beginTime = endTime.minusMonths(11).with(TemporalAdjusters.firstDayOfMonth())
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        //上线时间为2022-05-01，仅展示2022年5月以后的月份及对应数据
        LocalDateTime beginTime1 = LocalDate.of(2022, 5, 1).atStartOfDay();
        if(beginTime.isBefore(beginTime1)){
            beginTime = beginTime1;
        }
        List<BaseVO> vos = baseMapper.queryProjectNumTrend(beginTime, endTime);

        List<String> nameList = Lists.newLinkedList();
        List<String> valueList = Lists.newLinkedList();
        Map<String, BaseVO> map = vos.stream().collect(Collectors.toMap(BaseVO::getName, e -> e));

        long sum = 0; //项目总数
        while (!endTime.isBefore(beginTime)){
            int i = beginTime.getMonthValue();
            String month = Convert.numberToChinese(i, false);
            nameList.add((i < 10 ? month : StringUtils.substring(month, 1)) + "月");
            sum += map.containsKey(i + "") ? Long.parseLong(map.get(i + "").getValue()) : 0;
            valueList.add(sum + "");
            beginTime = beginTime.plusMonths(1);
        }
        List<BaseValueVO> list = Lists.newArrayList();
        list.add(BaseValueVO.builder().name("quantity").value(valueList).build());
        list.add(BaseValueVO.builder().name("coordinate").value(nameList).build());
        return list;
    }

    /**
     * 大屏-数据统计-月度总投资额与引资金额需求趋势
     * @Param:
     * @return:
     */
    public List<BaseValueVO> queryProjectAmountTrend() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime beginTime = endTime.minusMonths(11).with(TemporalAdjusters.firstDayOfMonth())
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        //上线时间为2022-05-01，仅展示2022年5月以后的月份及对应数据
        LocalDateTime beginTime1 = LocalDate.of(2022, 5, 1).atStartOfDay();
        if(beginTime.isBefore(beginTime1)){
            beginTime = beginTime1;
        }
        List<ProjectAmountVo> vos = baseMapper.queryProjectAmountTrend(beginTime, endTime);

        List<String> nameList = Lists.newLinkedList();
        List<String> investmentTotalList = Lists.newLinkedList();
        List<String> fundingAmountList = Lists.newLinkedList();
        Map<String, ProjectAmountVo> map = vos.stream().collect(Collectors.toMap(ProjectAmountVo::getName, e -> e));
        while (!endTime.isBefore(beginTime)) {
            int i = beginTime.getMonthValue();
            String month = Convert.numberToChinese(i, false);
            nameList.add((i < 10 ? month : StringUtils.substring(month, 1)) + "月");
            investmentTotalList.add(map.containsKey(i + "") ? map.get(i + "").getInvestmentTotal() : "0");
            fundingAmountList.add(map.containsKey(i + "") ? map.get(i + "").getFundingAmount() : "0");
            beginTime = beginTime.plusMonths(1);
        }
        List<BaseValueVO> list = Lists.newArrayList();
        list.add(BaseValueVO.builder().name("investmentTotalQuantity").value(investmentTotalList).build());
        list.add(BaseValueVO.builder().name("fundingAmountQuantity").value(fundingAmountList).build());
        list.add(BaseValueVO.builder().name("coordinate").value(nameList).build());
        return list;
    }
}
