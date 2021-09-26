package com.yjtech.wisdom.tourism.portal.controller.system;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.validate.CreateGroup;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.system.domain.Platform;
import com.yjtech.wisdom.tourism.system.service.PlatformService;
import com.yjtech.wisdom.tourism.system.vo.PlatformVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 平台信息维护
 *
 * @author liuhong
 * @date 2021-07-02 15:43
 */
@RestController
@RequestMapping("/system/platform")
public class PlatformController {

    @Autowired
    private PlatformService platformService;

    /**
     * 查询当前设置的平台信息
     *
     * @return 响应结果
     */
    @PostMapping("queryForDetail")
    public JsonResult<PlatformVO> queryForDetail() {
        Platform platform = platformService.getPlatform();
        if (Objects.isNull(platform)) {
            return JsonResult.success();
        }
        return JsonResult.success(BeanMapper.map(platform, PlatformVO.class));
    }


    /**
     * 编辑平台信息
     *
     * @param params 平台信息
     * @return 响应结果
     */
    @PreAuthorize("@ss.hasPermi('system:platform:update')")
    @PostMapping("update")
    public JsonResult<?> update(@RequestBody @Validated PlatformVO params) {
        Platform platform = BeanMapper.map(params, Platform.class);
        platformService.savePlatform(platform);
        return JsonResult.success();
    }
}
