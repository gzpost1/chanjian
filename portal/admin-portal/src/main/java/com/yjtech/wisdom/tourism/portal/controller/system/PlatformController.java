package com.yjtech.wisdom.tourism.portal.controller.system;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.PlatformDefaultTimeTypeEnum;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
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
        //时间筛选类型为其他时，校验开始时间与结束时间
        if(PlatformDefaultTimeTypeEnum.PLATFORM_DEFAULT_TIME_TYPE_ELSE.getValue().equals(params.getTimeSelectType())){
            if(null == params.getDefaultBeginTime() || null == params.getDefaultEndTime()){
                throw new CustomException(ErrorCode.PARAM_MISS, "编辑失败：开始时间或结束时间不能为空");
            }
            if(!params.getDefaultBeginTime().isBefore(params.getDefaultEndTime())){
                throw new CustomException(ErrorCode.PARAM_WRONG, "编辑失败：时间区间设置不合法");
            }
        }
        Platform platform = BeanMapper.map(params, Platform.class);
        platformService.savePlatform(platform);
        return JsonResult.success();
    }
}
