package com.yjtech.wisdom.tourism.portal.controller.project;

import com.yjtech.wisdom.tourism.common.annotation.IgnoreAuth;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.project.dto.ProjectNumQueryParam;
import com.yjtech.wisdom.tourism.project.dto.ProjectOpenApiDTO;
import com.yjtech.wisdom.tourism.project.dto.ProjectOpenApiQueryParam;
import com.yjtech.wisdom.tourism.project.service.ProjectOpenApiService;
import com.yjtech.wisdom.tourism.project.vo.ProjectNumVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 对外接口 项目
 */
@RestController
@RequestMapping("/screen/project/openapi")
public class ProjectOpenApiController {
    @Autowired
    private ProjectOpenApiService apiService;

    /**
     * 查询列表
     *
     */
    @IgnoreAuth
    @PostMapping("/queryForList")
    public JsonResult<List<ProjectOpenApiDTO>> queryForList(@RequestBody ProjectOpenApiQueryParam param) {
        return JsonResult.success(apiService.getList(param));
    }

    /**
     * 获取详情
     * @param idParam
     * @return
     */
    @IgnoreAuth
    @PostMapping("/queryForDetail")
    public JsonResult<ProjectOpenApiDTO> queryForDetail(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(apiService.queryForDetail(idParam));
    }

    /**
     * 获取所有标签
     * @return
     */
    @IgnoreAuth
    @PostMapping("/getLabelList")
    public JsonResult<List<String>> getLabelList() {
        return JsonResult.success(apiService.getLabelList());
    }



    /**
     * 获取所有项目数及投资总额
     */
    @IgnoreAuth
    @PostMapping("/getAllProjectNum")
    public JsonResult<ProjectNumVO>  getAllProjectNum(@RequestBody ProjectNumQueryParam param){
        ProjectNumVO projectNumVO= apiService.getAllProjectNum(param);
        return JsonResult.success(projectNumVO);
    }

    /**
     * 获取所有项目数及投资总额（根据区县聚合）
     */
    @IgnoreAuth
    @PostMapping("/getProjectNumByAreaCode")
    public JsonResult<List<ProjectNumVO>>  getProjectNumByAreaCode(@RequestBody ProjectNumQueryParam param){
        List<ProjectNumVO> projectNumVOs= apiService.getProjectNumByAreaCode(param);
        return JsonResult.success(projectNumVOs);
    }

    /**
     * 获取所有项目数及投资总额（根据标签聚合）
     */
    @IgnoreAuth
    @PostMapping("/getProjectNumByLabel")
    public JsonResult<List<ProjectNumVO>>  getProjectNumByLabel(@RequestBody ProjectNumQueryParam param){
        List<ProjectNumVO> projectNumVOs= apiService.getProjectNumByLabel(param);
        return JsonResult.success(projectNumVOs);
    }

    /**
     * 获取所有项目数及投资总额（根据月份聚合）
     */
    @IgnoreAuth
    @PostMapping("/getProjectNumByMonth")
    public JsonResult<List<ProjectNumVO>>  getProjectNumByMonth(@RequestBody ProjectNumQueryParam param){
        List<ProjectNumVO> projectNumVOs= apiService.getProjectNumByMonth(param);
        return JsonResult.success(projectNumVOs);
    }

    /**
     * 获取所有地区
     * @return
     */
    @IgnoreAuth
    @PostMapping("/getAreaList")
    public JsonResult<List<String>> getAreaList() {
        return JsonResult.success(apiService.getAreaList());
    }
}
