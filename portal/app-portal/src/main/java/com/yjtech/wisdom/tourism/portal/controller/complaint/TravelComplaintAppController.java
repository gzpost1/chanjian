package com.yjtech.wisdom.tourism.portal.controller.complaint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintDTO;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintListDTO;
import com.yjtech.wisdom.tourism.command.service.travelcomplaint.TravelComplaintService;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintCreateVO;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintDealVO;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintQueryVO;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintUpdateVO;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.TravelComplaintTypeEnum;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.framework.web.service.TokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

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
        IPage<TravelComplaintListDTO> page = travelComplaintService.queryForPage(vo);
        return JsonResult.success(page);
    }


}
