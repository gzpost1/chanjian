package com.yjtech.wisdom.tourism.portal.controller.complaint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintDTO;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintListDTO;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintStatusStatisticsDTO;
import com.yjtech.wisdom.tourism.command.service.travelcomplaint.TravelComplaintService;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintCreateVO;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintDealVO;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintQueryVO;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintUpdateVO;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.TravelComplaintStatusEnum;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.framework.web.service.TokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * app-旅游投诉
 *
 * @Author horadirm
 * @Date 2021/7/21 9:38
 */
@RestController
@RequestMapping("/travelComplaint/app/")
public class TravelComplaintAppController {

    @Autowired
    private TravelComplaintService travelComplaintService;
    @Autowired
    private TokenService tokenService;


    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @PostMapping("create")
    public JsonResult create(@RequestBody @Valid TravelComplaintCreateVO vo) {
        //校验投诉类型
        travelComplaintService.checkType(vo.getComplaintType(), vo.getComplaintObject(), vo.getObjectId());
        return JsonResult.success(travelComplaintService.create(vo));
    }

    /**
     * 编辑
     *
     * @param vo
     * @return
     */
    @PostMapping("update")
    public JsonResult update(@RequestBody @Valid TravelComplaintUpdateVO vo) {
        //校验投诉类型
        travelComplaintService.checkType(vo.getComplaintType(), vo.getComplaintObject(), vo.getObjectId());
        return JsonResult.success(travelComplaintService.modify(vo));
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

    /**
     * 处理旅游投诉
     *
     * @param vo
     * @return
     */
    @PostMapping("/dealTravelComplaint")
    public JsonResult dealTravelComplaint(@RequestBody @Valid TravelComplaintDealVO vo) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return JsonResult.success(travelComplaintService.dealTravelComplaint(vo, loginUser.getUser()));
    }

    /**
     * 查询分页
     *
     * @param vo
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<TravelComplaintListDTO>> queryForPage(@RequestBody @Valid TravelComplaintQueryVO vo) {
        //获取当前用户信息
        SysUser user = tokenService.getLoginUser(ServletUtils.getRequest()).getUser();
        //状态为待处理、已处理
        if(null != vo.getStatus() && TravelComplaintStatusEnum.DEAL_STATUS.contains(vo.getStatus())){
            //设置处理人id
            vo.setAcceptUserId(user.getUserId());
            vo.setCreateUser(null);
        }else {
            //设置创建人id
            vo.setCreateUser(user.getUserId());
            vo.setAcceptUserId(null);
        }

        IPage<TravelComplaintListDTO> page = travelComplaintService.queryForPage(vo);
        return JsonResult.success(page);
    }

    /**
     * 查询状态统计
     *
     * @param vo
     * @return
     */
    @PostMapping("/queryStatusStatistics")
    public JsonResult<TravelComplaintStatusStatisticsDTO> queryStatusStatistics(@RequestBody @Valid TravelComplaintQueryVO vo) {
        //获取当前用户
        SysUser user = tokenService.getLoginUser(ServletUtils.getRequest()).getUser();
        //默认处理人为当前用户
        vo.setAcceptUserId(user.getUserId());
        //默认创建者为当前用户
        vo.setCreateUser(user.getUserId());
        //默认配备状态为启用
        vo.setEquipStatus(EntityConstants.ENABLED);

        TravelComplaintStatusStatisticsDTO dto = travelComplaintService.queryStatusStatistics(vo);

        return JsonResult.success(dto);
    }


}
