package com.yjtech.wisdom.tourism.portal.controller.audit;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.bigscreen.dto.AuditCompanyParam;
import com.yjtech.wisdom.tourism.bigscreen.dto.DataPermissionsParam;
import com.yjtech.wisdom.tourism.bigscreen.dto.TbRegisterInfoParam;
import com.yjtech.wisdom.tourism.bigscreen.dto.UpdateBlacklistParam;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbAuditInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.service.TbAuditInfoService;
import com.yjtech.wisdom.tourism.bigscreen.service.TbRegisterInfoService;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.AreaUtils;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.position.entity.TbDictAreaEntity;
import com.yjtech.wisdom.tourism.position.service.TbDictAreaService;
import com.yjtech.wisdom.tourism.project.service.TbProjectInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * v1.5_客商库
 *
 * @author Mujun
 * @since 2022-03-04
 */
@Slf4j
@RestController
@RequestMapping("/company-manager")
public class CompanyManagerController extends BaseCurdController<TbRegisterInfoService, TbRegisterInfoEntity, TbRegisterInfoParam> {
    @Autowired
    private TbRegisterInfoService registerInfoService;

    @Autowired
    private TbAuditInfoService auditInfoService;

    @Autowired
    private TbProjectInfoService tbProjectInfoService;

    @Autowired
    private TbDictAreaService dictAreaService;

    /**
     * 企业详情
     *
     * @param idParam
     * @return
     */
    @Override
    @PostMapping("/queryForDetail")
    public JsonResult<TbRegisterInfoEntity> queryForDetail(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(registerInfoService.getById(idParam.getId()));
    }

    /**
     * 企业管理 分页查询
     *
     * @param params
     * @return
     */
    @Override
    @PostMapping("/queryForPage")
    public JsonResult<Page<TbRegisterInfoEntity>> queryForPage(@RequestBody @Valid TbRegisterInfoParam params) {
        if (StringUtils.isNotBlank(params.getAreaCode())) {
            params.setAreaCode(AreaUtils.trimCode(params.getAreaCode()));
        }
        if (StringUtils.isNotBlank(params.getLikeAreaCode())) {
            params.setLikeAreaCode(AreaUtils.trimCode(params.getLikeAreaCode()));
        }
        Page<TbRegisterInfoEntity> page = registerInfoService.page(params);
        List<TbRegisterInfoEntity> records = page.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            records.stream().map(TbRegisterInfoEntity::getAreaCode).collect(Collectors.toList());
            LambdaQueryWrapper<TbDictAreaEntity> areaQuery = Wrappers.lambdaQuery();
            List<TbDictAreaEntity> areaList = dictAreaService.list(areaQuery);
            Map<String, TbDictAreaEntity> areaMap = areaList.stream()
                    .collect(Collectors.toMap(e -> e.getCode().substring(0, 6), e -> e));
            for (TbRegisterInfoEntity record : records) {
                TbDictAreaEntity area = areaMap.get(record.getAreaCode());
                if (area != null) {
                    StringBuilder sb = new StringBuilder(area.getProvinceName());
                    if (StringUtils.isNotBlank(area.getCityName())) {
                        sb.append("-").append(area.getCityName());
                    }
                    if (StringUtils.isNotBlank(area.getCountyName())) {
                        sb.append("-").append(area.getCountyName());
                    }
                    record.setAreaName(sb.toString());
                }
            }
        }
        return JsonResult.success(page);
    }

    /**
     * 企业管理 列表查询
     *
     * @param params
     * @return
     */
    @PostMapping("/queryForList")
    public JsonResult<List<TbRegisterInfoEntity>> queryForList(@RequestBody @Valid TbRegisterInfoParam params) {
        TbRegisterInfoEntity entity = BeanMapper.copyBean(params, TbRegisterInfoEntity.class);
        return JsonResult.success(registerInfoService.list(entity));
    }

    /**
     * 修改黑名单状态
     *
     * @param params
     * @return
     */
    @PostMapping("/updateBlacklist")
    public JsonResult updateBlacklist(@RequestBody @Valid UpdateBlacklistParam params) {
        TbRegisterInfoEntity queryRegisterInfoEntity = registerInfoService.getById(params.getId());
        AssertUtil.isFalse(Objects.isNull(queryRegisterInfoEntity), "该企业不存在");

        // 判断该企业 是否 绑定了已发布的项目；绑定则不能修改黑名单状态
        int bindProjectNum = tbProjectInfoService.findBingProject(queryRegisterInfoEntity.getId());
        if (0 == bindProjectNum) {
            queryRegisterInfoEntity.setBlacklist(params.getBlacklist());
            registerInfoService.updateById(queryRegisterInfoEntity);
            return JsonResult.success();
        }
        return JsonResult.error("拉入黑名单失败，可能存在项目绑定了该企业");
    }

    /**
     * 审核
     *
     * @param params
     * @return
     */
    @PostMapping("/auditCompany")
    public JsonResult auditCompany(@RequestBody @Valid AuditCompanyParam params) {
        TbRegisterInfoEntity queryRegisterInfoEntity = registerInfoService.getById(params.getId());
        AssertUtil.isFalse(Objects.isNull(queryRegisterInfoEntity), "该企业不存在");
        queryRegisterInfoEntity.setAuditStatus(params.getAuditStatus());
        queryRegisterInfoEntity.setAuditTime(LocalDateTime.now());
        TbAuditInfoEntity auditInfoEntity = TbAuditInfoEntity.builder()
                .companyId(queryRegisterInfoEntity.getId())
                .auditStatus(params.getAuditStatus())
                .auditComment(params.getAuditComment())
                .build();
        registerInfoService.updateById(queryRegisterInfoEntity);
        auditInfoService.save(auditInfoEntity);
        return JsonResult.success("审核成功");
    }

    /**
     * 审批记录
     *
     * @param idParam
     * @return
     */
    @PostMapping("/auditRecord")
    public JsonResult auditRecord(@RequestBody @Valid IdParam idParam) {
        TbRegisterInfoEntity queryRegisterInfoEntity = registerInfoService.getById(idParam.getId());
        AssertUtil.isFalse(Objects.isNull(queryRegisterInfoEntity), "该企业不存在");
        List<TbAuditInfoEntity> list = auditInfoService.queryAuditRecord(idParam.getId());
        return JsonResult.success(list);
    }

    /**
     * 修改数据统计权限
     *
     * @param param
     * @return
     */
    @PostMapping("/changeDataPermissions")
    public JsonResult changeDataPermissions(@RequestBody @Valid DataPermissionsParam param) {
        TbRegisterInfoEntity entity = Optional.ofNullable(registerInfoService.getById(param.getId()))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "该企业不存在"));
        entity.setDataPermissions(param.getDataPermissions());
        registerInfoService.updateById(entity);
        return JsonResult.success();
    }

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
     * 查询项目方的所有企业
     *
     * @return
     */
    @PostMapping("/queryProjectCompanyById")
    public JsonResult<TbRegisterInfoEntity> queryProjectCompanyById(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(registerInfoService.getById(idParam.getId()));
    }

    @PostMapping("/update")
    @Override
    public JsonResult update(@RequestBody @Valid TbRegisterInfoEntity entity) {
        entity.setAuditStatus(1);
        return super.update(entity);
    }

    @PostMapping("/delete")
    @Override
    public JsonResult delete(@RequestBody @Valid IdParam params) {
        // 判断该企业 是否 绑定了已发布的项目
        int bindProjectNum = tbProjectInfoService.findBingProject(params.getId());
        AssertUtil.isFalse(bindProjectNum > 0, "该数据已绑定项目，无法删除");
        return super.delete(params);
    }
}
