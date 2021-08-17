package com.yjtech.wisdom.tourism.portal.controller.complaint;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintDTO;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintListDTO;
import com.yjtech.wisdom.tourism.command.service.travelcomplaint.TravelComplaintService;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintQueryVO;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintScreenQueryVO;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 旅游投诉 大屏
 *
 * @Author horadirm
 * @Date 2021/7/27 16:29
 */
@RestController
@RequestMapping("/travelComplaint/screen/")
public class TravelComplaintScreenController {

    @Autowired
    private TravelComplaintService travelComplaintService;


    /**
     * 查询旅游投诉量
     * @return
     */
    @PostMapping("queryTravelComplaintTotal")
    public JsonResult queryTravelComplaintTotal(@RequestBody @Valid TravelComplaintScreenQueryVO vo) {
        return JsonResult.success(travelComplaintService.queryTravelComplaintTotal(vo));
    }

    /**
     * 查询旅游投诉类型分布
     * @return
     */
    @PostMapping("queryComplaintTypeDistribution")
    public JsonResult<List<BasePercentVO>> queryComplaintTypeDistribution(@RequestBody @Valid TravelComplaintScreenQueryVO vo) {
        //默认启用
        vo.setEquipStatus(EntityConstants.ENABLED);
        return JsonResult.success(travelComplaintService.queryComplaintTypeDistribution(vo));
    }

    /**
     * 查询旅游投诉状态分布
     * @return
     */
    @PostMapping("queryComplaintStatusDistribution")
    public JsonResult<List<BasePercentVO>> queryComplaintStatusDistribution(@RequestBody @Valid TravelComplaintScreenQueryVO vo) {
        //默认启用
        vo.setEquipStatus(EntityConstants.ENABLED);
        return JsonResult.success(travelComplaintService.queryComplaintStatusDistribution(vo));
    }

    /**
     * 查询旅游投诉类型Top排行
     * @return
     */
    @PostMapping("queryComplaintTopByType")
    public JsonResult<List<BaseVO>> queryComplaintTopByType(@RequestBody @Valid TravelComplaintScreenQueryVO vo) {
        //默认启用
        vo.setEquipStatus(EntityConstants.ENABLED);
        return JsonResult.success(travelComplaintService.queryComplaintTopByType(vo));
    }

    /**
     * 查询本年旅游投诉趋势
     * @return
     */
    @PostMapping("queryComplaintAnalysis")
    public JsonResult<List<AnalysisBaseInfo>> queryComplaintAnalysis(@RequestBody @Valid TravelComplaintScreenQueryVO vo) {
        //默认启用
        vo.setEquipStatus(EntityConstants.ENABLED);
        return JsonResult.success(travelComplaintService.queryComplaintAnalysis(vo));
    }

    /**
     * 查询分页
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForPage")
    public JsonResult<IPage<TravelComplaintListDTO>> queryForPage(@RequestBody @Valid TravelComplaintScreenQueryVO vo) {
        //默认启用
        vo.setEquipStatus(EntityConstants.ENABLED);
        //构建查询参数
        TravelComplaintQueryVO query = BeanUtil.copyProperties(vo, TravelComplaintQueryVO.class);

        IPage<TravelComplaintListDTO> page = travelComplaintService.queryForPage(query);
        return JsonResult.success(page);
    }

    /**
     * 根据id查询
     *
     * @param idParam
     * @return
     */
    @PostMapping("queryById")
    public JsonResult<TravelComplaintDTO> queryById(@RequestBody @Valid IdParam idParam) {
        TravelComplaintDTO dto = travelComplaintService.queryById(idParam.getId());
        return JsonResult.success(dto);
    }

}
