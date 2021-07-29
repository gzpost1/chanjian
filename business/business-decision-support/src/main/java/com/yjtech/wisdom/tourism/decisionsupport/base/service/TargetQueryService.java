package com.yjtech.wisdom.tourism.decisionsupport.base.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yjtech.wisdom.tourism.common.path.DistrictPathEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.decisionsupport.base.dto.LastMonthDto;
import com.yjtech.wisdom.tourism.decisionsupport.base.dto.TargetDto;
import com.yjtech.wisdom.tourism.decisionsupport.base.dto.WarnConfigDto;
import com.yjtech.wisdom.tourism.decisionsupport.base.entity.WarnConfigEntity;
import com.yjtech.wisdom.tourism.decisionsupport.base.mapper.TargetLibraryMapper;
import com.yjtech.wisdom.tourism.decisionsupport.base.mapper.WarnConfigMapper;
import com.yjtech.wisdom.tourism.decisionsupport.base.vo.VisitNumberVo;
import com.yjtech.wisdom.tourism.decisionsupport.base.vo.WarnConfigVo;
import com.yjtech.wisdom.tourism.integration.service.DistrictBigDataService;
import com.yjtech.wisdom.tourism.system.service.PlatformService;
import com.yjtech.wisdom.tourism.system.vo.PlatformVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 指标库基础查询方法
 *
 * @author renguangqian
 * @date 2021/7/27 15:24
 */
@Service
public class TargetQueryService {

    @Autowired
    private TargetLibraryMapper targetLibraryMapper;

    @Autowired
    private WarnConfigMapper warnConfigMapper;

    @Autowired
    private PlatformService platformService;

    @Autowired
    private DistrictBigDataService districtBigDataService;

    /**
     * GET 指标列表查询
     */
    public List<TargetDto> queryTarget() {
        return JSONObject.parseArray(
                JSONObject.toJSONString(targetLibraryMapper.selectList(null)),
                TargetDto.class);
    }

    /**
     * POST 根据指标id查询 预警配置项
     *
     * @param vo
     * @return
     */
    public List<WarnConfigDto> queryWarnConfig(WarnConfigVo vo) {
        return JSONObject.parseArray(
                JSONObject.toJSONString(warnConfigMapper.selectList(
                        new LambdaQueryWrapper<WarnConfigEntity>()
                                .eq(WarnConfigEntity::getTargetId, vo.getTargetId())
                )),
                WarnConfigDto.class);
    }

    /**
     * GET 获取统计年月
     *
     * @return
     */
    public LastMonthDto queryLastMonth() {
        return LastMonthDto.builder()
                .yearMonth(DateTimeUtil.getCurrentLastMonthStr())
               .build();
    }

    /**
     * GET 获取平台简称
     *
     * @return
     */
    public PlatformVO queryPlatformSimpleName() {
        return JSONObject.parseObject(
                JSONObject.toJSONString(platformService.getPlatform()),
                PlatformVO.class);
    }

    /**
     * 省外客流数据查询
     */
    public void queryProvinceOutsideNumber () {
        String beginDate = DateTimeUtil.getCurrentLastMonthFirstDayStr();
        String endTime = DateTimeUtil.getCurrentLastMonthLastDayStr();
        VisitNumberVo visitNumberVo = VisitNumberVo.builder().statisticsType("12").beginDate(beginDate).endDate(endTime).build();
        districtBigDataService.requestDistrict(DistrictPathEnum.VISIT_NUMBER.getPath(), visitNumberVo, DistrictPathEnum.VISIT_NUMBER.getDesc());
    }

    /**
     * 省外客流环比数据查询
     */
    public void queryHbProvinceOutside () {

    }
    /**
     * 省外客流同比数据查询
     */
    public void queryTbProvinceOutside () {

    }



}
