package com.yjtech.wisdom.tourism.portal.controller.wechat;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.wechat.wechat.dto.CodeTemplateForm;
import com.yjtech.wisdom.tourism.wechat.wechat.entity.CodeTemplate;
import com.yjtech.wisdom.tourism.wechat.wechat.query.CodeTemplateQuery;
import com.yjtech.wisdom.tourism.wechat.wechat.service.CodeTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 小程序模板管理
 *
 * @author wuyongchong
 * @since 2020/1/15.
 */
@RestController
@RequestMapping("/code-template")
public class CodeTemplateController {

    @Autowired
    private CodeTemplateService codeTemplateService;

    /**
     * 模板列表
     */
    @PostMapping("/list")
    public JsonResult list(@RequestBody @Valid CodeTemplateQuery query) {
        QueryWrapper<CodeTemplate> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query.getTemplateName())) {
            queryWrapper.like("template_name", query.getTemplateName().trim());
        }
        if (Objects.nonNull(query.getStatus())) {
            queryWrapper.eq("status", query.getStatus());
        }
        queryWrapper.orderByDesc("create_time", "id");
        List<CodeTemplate> list = codeTemplateService.list(queryWrapper);
        return JsonResult.success(list);
    }

    /**
     * 模板分页列表
     */
    @PostMapping("/pageList")
    @ResponseBody
    public JsonResult<IPage<CodeTemplate>> pageList(@RequestBody @Valid CodeTemplateQuery query) {
        QueryWrapper<CodeTemplate> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query.getTemplateName())) {
            queryWrapper.like("template_name", query.getTemplateName().trim());
        }
        if (Objects.nonNull(query.getStatus())) {
            queryWrapper.eq("status", query.getStatus());
        }
        queryWrapper.orderByDesc("create_time", "id");
        IPage<CodeTemplate> pageResult = codeTemplateService
                .page(new Page<CodeTemplate>(query.getPageNo(), query.getPageSize()), queryWrapper);

        return JsonResult.success(pageResult);
    }

    /**
     * 获取模板详情
     */
    @PostMapping("/selectOne")
    public JsonResult selectOne(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(codeTemplateService.getById(idParam.getId()));
    }

    /**
     * 新增模板
     */
    @PostMapping("/create")
    @ResponseBody
    public JsonResult create(@RequestBody @Valid CodeTemplateForm postBody) {
        if (null != postBody.getId()) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "参数错误");
        }
        CodeTemplate entity = BeanMapper.map(postBody, CodeTemplate.class);
        entity.setStatus(EntityConstants.ENABLED);
        codeTemplateService.save(entity);
        return JsonResult.ok();
    }

    /**
     * 修改模板
     */
    @PostMapping("/update")
    @ResponseBody
    public JsonResult update(@RequestBody @Valid CodeTemplateForm postBody) {
        CodeTemplate entity = BeanMapper.map(postBody, CodeTemplate.class);
        codeTemplateService.updateById(entity);
        return JsonResult.ok();
    }

    /**
     * 启用禁用模板
     */
    @PostMapping("/updateStatus")
    @ResponseBody
    public JsonResult updateStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        CodeTemplate entity = Optional
                .ofNullable(codeTemplateService.getById(updateStatusParam.getId()))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "找不到记录"));
        entity.setStatus(updateStatusParam.getStatus());
        codeTemplateService.updateById(entity);
        return JsonResult.ok();
    }

}
