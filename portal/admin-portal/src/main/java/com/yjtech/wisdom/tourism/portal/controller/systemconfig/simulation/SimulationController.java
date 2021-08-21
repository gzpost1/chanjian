package com.yjtech.wisdom.tourism.portal.controller.systemconfig.simulation;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationQueryDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.complaint.SimulationTravelComplaintDTO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.praise.SimulationPraiseDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.ticket.SimulationTicketDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.wifi.SimulationWifiDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.service.SimulationConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 后台管理-系统配置-模拟数据
 *
 * @author 李波
 * @description: 后台管理-系统配置-大屏菜单配置
 * @date 2021/7/211:40
 */
@RestController
@RequestMapping("/systemconfig/simulation")
public class SimulationController {

    @Autowired
    private SimulationConfigService service;

    /**
     * 详情
     *
     * @param
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult queryForDetail(@RequestBody @Valid SimulationQueryDto query) {
        return JsonResult.success(service.queryForDetail(query));
    }

    /**
     * 新增口碑
     *
     * @param
     * @return
     */
    @PostMapping("/savePraise")
    public JsonResult savePraise(@RequestBody SimulationPraiseDto dto) {
        service.generateRandom(dto);
        service.saveUpdated(dto);
        return JsonResult.success();
    }

    /**
     * 新增口碑
     *
     * @param
     * @return
     */
    @PostMapping("/saveWifi")
    public JsonResult saveWifi(@RequestBody SimulationWifiDto dto) {
        service.generateRandom(dto);
        service.saveUpdated(dto);
        return JsonResult.success();
    }

    /**
     * 新增票务
     *
     * @param
     * @return
     */
    @PostMapping("/saveTicket")
    public JsonResult saveTicket(@RequestBody SimulationTicketDto dto) {
        service.generateRandom(dto);
        service.saveUpdated(dto);
        return JsonResult.success();
    }

    /**
     * 新增旅游投诉
     *
     * @param
     * @return
     */
    @PostMapping("/saveTravelComplaint")
    public JsonResult saveTravelComplaint(@RequestBody SimulationTravelComplaintDTO dto) {
        service.generateRandom(dto);
        service.saveUpdated(dto);
        return JsonResult.success();
    }


}
