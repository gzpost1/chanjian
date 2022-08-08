package com.yjtech.wisdom.tourism.portal.controller.index;

import com.yjtech.wisdom.tourism.bigscreen.service.TbRegisterInfoService;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.index.DataAnalysisDTO;
import com.yjtech.wisdom.tourism.common.bean.index.DataStatisticsDTO;
import com.yjtech.wisdom.tourism.common.bean.index.DataStatisticsQueryVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.project.service.TbProjectInfoService;
import com.yjtech.wisdom.tourism.resource.talents.service.TalentsPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 管理后台-首页
 *
 * @author horadirm
 * @date 2022/5/24 11:46
 */
@RestController
@RequestMapping("/index/")
public class IndexController {

    @Autowired
    private TbProjectInfoService tbProjectInfoService;
    @Autowired
    private TbRegisterInfoService tbRegisterInfoService;
    @Autowired
    private TalentsPoolService talentsPoolService;



    /**
     * 查询数据统计-项目数量
     *
     * @return
     */
    @PostMapping("queryDataStatisticsByProject")
    public JsonResult<DataStatisticsDTO> queryDataStatisticsByProject() {
        return JsonResult.success(tbProjectInfoService.queryDataStatistics());
    }

    /**
     * 查询数据统计-企业注册数
     *
     * @return
     */
    @PostMapping("queryDataStatisticsByRegister")
    public JsonResult<DataStatisticsDTO> queryDataStatisticsByRegister() {
        return JsonResult.success(tbRegisterInfoService.queryDataStatistics());
    }

    /**
     * 查询数据统计-企业黑名单
     *
     * @return
     */
    @PostMapping("queryDataStatisticsByBlackList")
    public JsonResult<DataStatisticsDTO> queryDataStatisticsByBlackList() {
        return JsonResult.success(tbRegisterInfoService.queryDataStatisticsByBlackList());
    }

    /**
     * 查询数据统计-人才数
     *
     * @return
     */
    @PostMapping("queryDataStatisticsByTalents")
    public JsonResult<DataStatisticsDTO> queryDataStatisticsByTalents() {
        return JsonResult.success(talentsPoolService.queryDataStatistics());
    }

    /**
     * 查询趋势-项目数、企业数
     *
     * @param vo
     * @return
     */
    @PostMapping("queryAnalysis")
    public JsonResult<DataAnalysisDTO> queryAnalysis(@RequestBody @Valid DataStatisticsQueryVO vo) {
        List<BaseVO> list1 = tbProjectInfoService.queryAnalysis(vo);
        List<BaseVO> list2 = tbRegisterInfoService.queryAnalysis(vo);
        return JsonResult.success(new DataAnalysisDTO(list1, list2));
    }

}
