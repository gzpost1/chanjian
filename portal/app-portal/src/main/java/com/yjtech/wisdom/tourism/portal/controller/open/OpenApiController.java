package com.yjtech.wisdom.tourism.portal.controller.open;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.annotation.IgnoreAuth;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AreaUtils;
import com.yjtech.wisdom.tourism.project.dto.OpenProjectInfoParam;
import com.yjtech.wisdom.tourism.project.dto.ProjectQuery;
import com.yjtech.wisdom.tourism.project.entity.OpenProjectInfoEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectInfoEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectResourceEntity;
import com.yjtech.wisdom.tourism.project.service.OpenProjectInfoService;
import com.yjtech.wisdom.tourism.project.service.TbProjectInfoService;
import com.yjtech.wisdom.tourism.project.service.TbProjectLabelRelationService;
import com.yjtech.wisdom.tourism.project.service.TbProjectResourceService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 对外提供项目数据以及客商数据接口
 */
@RestController
@RequestMapping("/open-api/")
public class OpenApiController {
    @Autowired
    OpenProjectInfoService projectInfoService;
    /**
     * 分页查询
     * @param params
     * @return
     */
    @PostMapping("/project/queryForPage")
    public JsonResult<Page<OpenProjectInfoEntity>> queryForPage(
            @RequestBody OpenProjectInfoParam params) {
        return JsonResult.success(projectInfoService.page(params));
    }

}
