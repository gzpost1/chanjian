package com.yjtech.wisdom.tourism.portal.controller.scenic;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.resource.scenic.entity.ScenicEntity;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import com.yjtech.wisdom.tourism.resource.scenic.service.ScenicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 景区app
 *
 * @author zc
 * @since 2021-08-03
 */
@RestController
@RequestMapping("/scenic/app")
public class ScenicAppController {

    @Autowired
    private ScenicService scenicService;

    /**
     * 分页查询
     * @Param:  query
     * @return:
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<ScenicEntity>> queryForPage(@RequestBody ScenicScreenQuery query) {
        return JsonResult.success(scenicService.queryForPage(query));
    }
}
