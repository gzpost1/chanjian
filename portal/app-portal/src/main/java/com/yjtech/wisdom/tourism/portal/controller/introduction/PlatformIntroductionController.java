package com.yjtech.wisdom.tourism.portal.controller.introduction;

import com.yjtech.wisdom.tourism.common.annotation.IgnoreAuth;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.resource.introduction.entity.PlatformIntroductionEntity;
import com.yjtech.wisdom.tourism.resource.introduction.service.PlatformIntroductionService;
import com.yjtech.wisdom.tourism.resource.introduction.vo.PlatformIntroductionQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 大屏（h5）-平台介绍
 *
 * @author horadirm
 * @date 2022/5/24 11:46
 */
@RestController
@RequestMapping("/screen/platformIntroduction/")
public class PlatformIntroductionController {

    @Autowired
    private PlatformIntroductionService platformIntroductionService;


    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    @IgnoreAuth
    @PostMapping("queryForList")
    public JsonResult<List<PlatformIntroductionEntity>> queryForList(@RequestBody @Valid PlatformIntroductionQueryVO vo) {
        return JsonResult.success(platformIntroductionService.queryForList(vo));
    }

}
