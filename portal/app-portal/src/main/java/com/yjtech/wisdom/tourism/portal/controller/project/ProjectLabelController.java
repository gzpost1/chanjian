package com.yjtech.wisdom.tourism.portal.controller.project;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.project.entity.TbProjectLabelEntity;
import com.yjtech.wisdom.tourism.project.service.TbProjectLabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 大屏-项目标签
 *
 * @date 2022/5/18 18:18
 * @author horadirm
 */
@Slf4j
@RestController
@RequestMapping("/screen/projectLabel/")
public class ProjectLabelController {

    @Autowired
    private TbProjectLabelService tbProjectLabelService;


    /**
     * 查询可用标签列表
     *
     * @return
     */
    @PostMapping("queryEnableForList")
    public JsonResult<List<TbProjectLabelEntity>> queryEnableForList() {
        return JsonResult.success(tbProjectLabelService.queryEnableForList());
    }

}
