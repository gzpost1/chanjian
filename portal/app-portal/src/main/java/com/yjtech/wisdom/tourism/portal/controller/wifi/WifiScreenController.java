package com.yjtech.wisdom.tourism.portal.controller.wifi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.bean.TimeBaseQuery;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.PageQuery;
import com.yjtech.wisdom.tourism.common.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.resource.wifi.dto.WifiBaseDto;
import com.yjtech.wisdom.tourism.resource.wifi.entity.WifiEntity;
import com.yjtech.wisdom.tourism.resource.wifi.query.WifiSummaryQuery;
import com.yjtech.wisdom.tourism.resource.wifi.service.*;
import com.yjtech.wisdom.tourism.resource.wifi.vo.WifiHotVo;
import com.yjtech.wisdom.tourism.resource.wifi.vo.WifiStatisticsVo;
import com.yjtech.wisdom.tourism.system.domain.IconSpotEnum;
import com.yjtech.wisdom.tourism.system.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


/**
 * 大屏wifi
 * @author zc
 * @create 2021-07-12 11:02
 */

@RestController
@RequestMapping("/wifi")
public class WifiScreenController {

    @Autowired
    private WifiTemporaryService temporaryService;
    @Autowired
    private WifiVisitorService visitorService;
    @Autowired
    private IconService iconService;
    @Autowired
    private WifiService wifiService;
    @Autowired
    private WifiSummaryService summaryService;
    
    /** 
     * wifi综合统计（首页）
     * @Param:  
     * @return:  
     * @Author: zc
     * @Date: 2021-07-15 
     */
    @GetMapping("wifiStatistics")
    public JsonResult<WifiStatisticsVo> queryWifiStatistics(){
        return JsonResult.success(wifiService.queryWifiStatistics());
    }

    /**
     * 1、注册来宾总数，2、当前在线来宾数，3、历史在线峰值
     * @Param:
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("temporaryList")
    public JsonResult<List<BaseVO>> queryTemporaryList() {
        return JsonResult.success(temporaryService.queryTemporaryList());
    }

    /**
     * 手机厂商TOP10
     * @Param:
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("phoneManufacturerTop10")
    public JsonResult<List<BaseVO>> queryPhoneManufacturerTop10(@RequestBody @Valid TimeBaseQuery query) {
        return JsonResult.success(visitorService.queryPhoneManufacturerTop10(query));
    }

    /**
     * 手机认证占比
     * @Param:
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("authType")
    public JsonResult<List<BasePercentVO>> queryAuthType() {
        return JsonResult.success(visitorService.queryAuthType());
    }

    /**
     * 点位
     * @Param:
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("queryForList")
    public JsonResult<List<WifiEntity>> queryForList(){
        List<WifiEntity> list = wifiService.list();
        if(CollectionUtils.isEmpty(list)){
            return JsonResult.success(new ArrayList<>());
        }
        return JsonResult.success(list);
    }

    /**
     * 热门点位
     * @Param:
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("hotWifi")
    public JsonResult<IPage<WifiHotVo>> queryHotWifi(@RequestBody PageQuery query){
        return JsonResult.success(wifiService.queryHotWifi(query));
    }

    /**
     * 今日连接数
     * @Param:
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("currentConnectNum")
    public JsonResult<List<BaseValueVO>> queryCurrentConnectNum(@RequestBody @Valid WifiSummaryQuery query){
        query.setWifiType(2);
        List<WifiBaseDto> dtoList = summaryService.getBaseMapper().queryCurrentConnectNum(query);
        return JsonResult.success(AnalysisUtils.MultipleBuildAnalysis(query, dtoList,WifiBaseDto::getQuantity));
    }

    /**
     * 连接时长
     * @Param:
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("connectionDuration")
    public JsonResult<List<BaseValueVO>> queryConnectionDuration(@RequestBody @Valid WifiSummaryQuery query){
        query.setWifiType(1);
        return JsonResult.success(summaryService.queryConnectionDuration(query));
    }
}
