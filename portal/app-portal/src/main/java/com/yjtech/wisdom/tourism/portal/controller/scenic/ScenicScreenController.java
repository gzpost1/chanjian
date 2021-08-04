package com.yjtech.wisdom.tourism.portal.controller.scenic;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicBaseVo;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicScreenPageVo;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicPageQuery;
import com.yjtech.wisdom.tourism.resource.scenic.service.ScenicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 景区大屏
 *
 * @author zc
 * @since 2021-08-03
 */
@RestController
@RequestMapping("/scenic/screen")
public class ScenicScreenController {

    @Autowired
    private ScenicService scenicService;

    /**
     * 分页查询
     * @Param:  query
     * @return:
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<ScenicScreenPageVo>> queryScreenForPage(@RequestBody ScenicPageQuery query) {
        query.setStatus((byte) 1);
        return JsonResult.success(scenicService.queryScreenForPage(query));
    }


    /**
     * 景区等级分布
     * @Param:  query
     * @return:
     */
    @PostMapping("/queryLevelDistribution")
    public JsonResult<List<ScenicBaseVo>> queryLevelDistribution() {
        return JsonResult.success(scenicService.queryLevelDistribution());
    }
}
