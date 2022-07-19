package com.yjtech.wisdom.tourism.portal.controller.audit;


import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.service.TbRegisterInfoService;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 大屏-客商库
 *
 * @author Mujun
 * @since 2022-03-04
 */
@Slf4j
@RestController
@RequestMapping("/screen/company-manager")
public class CompanyManagerController {
    @Autowired
    private TbRegisterInfoService registerInfoService;


    /**
     * 查询项目方的所有企业
     *
     * @return
     */
    @PostMapping("/queryProjectCompany")
    public JsonResult<List<TbRegisterInfoEntity>> queryProjectCompany() {
        return JsonResult.success(registerInfoService.queryProjectCompany());
    }


    /**
     * 查询项目方id的所有该企业
     *
     * @return
     */
    @PostMapping("/queryProjectCompanyById")
    public JsonResult<TbRegisterInfoEntity> queryProjectCompanyById(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(registerInfoService.getById(idParam.getId()));
    }

}
