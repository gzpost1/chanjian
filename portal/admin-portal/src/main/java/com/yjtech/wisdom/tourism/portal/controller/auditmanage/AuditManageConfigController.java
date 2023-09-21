package com.yjtech.wisdom.tourism.portal.controller.auditmanage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import com.yjtech.wisdom.tourism.resource.auditmanage.dto.AuditManageConfigCreateDto;
import com.yjtech.wisdom.tourism.resource.auditmanage.dto.AuditManageConfigUpdateCheckDto;
import com.yjtech.wisdom.tourism.resource.auditmanage.dto.AuditManageConfigUpdateDto;
import com.yjtech.wisdom.tourism.resource.auditmanage.entity.AuditManageConfig;
import com.yjtech.wisdom.tourism.resource.auditmanage.entity.AuditManageInfo;
import com.yjtech.wisdom.tourism.resource.auditmanage.entity.AuditManageProcess;
import com.yjtech.wisdom.tourism.resource.auditmanage.service.AuditManageConfigService;
import com.yjtech.wisdom.tourism.resource.auditmanage.service.AuditManageInfoService;
import com.yjtech.wisdom.tourism.resource.auditmanage.service.AuditManageProcessService;
import com.yjtech.wisdom.tourism.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 【后台】审核配置
 *
 * @author songjun
 * @since 2023/9/11
 */
@Slf4j
@RestController
@RequestMapping("/auditmanage/config")
public class AuditManageConfigController {
    @Autowired
    private AuditManageConfigService auditManageConfigService;
    @Autowired
    private AuditManageProcessService auditManageProcessService;
    @Autowired
    private AuditManageInfoService auditManageInfoService;
    @Autowired
    private SysUserService userService;

    /**
     * 分页
     *
     * @param query
     * @return
     */
    @PostMapping("page")
    public JsonResult<IPage<AuditManageConfig>> page(@RequestBody @Valid PageQuery query) {
        IPage<AuditManageConfig> page = auditManageConfigService.page(new Page<>(query.getPageNo(), query.getPageSize()));
        if (CollectionUtils.isNotEmpty(page.getRecords())) {
            List<Long> auditConfigIds = page.getRecords()
                    .stream()
                    .map(AuditManageConfig::getId)
                    .collect(Collectors.toList());
            // 查流程
            LambdaQueryWrapper<AuditManageProcess> processQuery = Wrappers.lambdaQuery();
            processQuery.in(AuditManageProcess::getConfigId, auditConfigIds);
            List<AuditManageProcess> processList = auditManageProcessService.list(processQuery);
            Map<Long, List<AuditManageProcess>> processMap = processList.stream()
                    .collect(Collectors.groupingBy(AuditManageProcess::getConfigId));
            //查用户
            List<Long> userIds = processList.stream().flatMap(e -> {
                if (CollectionUtils.isNotEmpty(e.getUserIds())) {
                    return e.getUserIds().stream();
                } else {
                    return Stream.empty();
                }
            }).collect(Collectors.toList());
            Map<Long, String> userIdNameMap = userService.selectUserListById(userIds)
                    .stream()
                    .collect(Collectors.toMap(SysUser::getUserId, SysUser::getNickName));
            // 绑定数据
            for (AuditManageConfig record : page.getRecords()) {
                List<AuditManageProcess> processes = processMap.get(record.getId());
                if (CollectionUtils.isNotEmpty(processes)) {
                    processes.sort(Comparator.comparing(AuditManageProcess::getSort));
                    record.setProcessList(processes);
                    String str = processes.stream()
                            .map(e -> e.getUserIds()
                                    .stream()
                                    .map(e1 -> userIdNameMap.getOrDefault(e1, "未知"))
                                    .collect(Collectors.joining(",")))
                            .collect(Collectors.joining("->"));
                    record.setProcessStr(str);
                }
            }
        }
        return JsonResult.success(page);
    }

    /**
     * 详情
     *
     * @param param
     * @return
     */
    @PostMapping("detail")
    public JsonResult<AuditManageConfig> detail(@RequestBody @Valid IdParam param) {
        AuditManageConfig byId = auditManageConfigService.getById(param.getId());
        if (byId != null) {
            LambdaQueryWrapper<AuditManageProcess> processQuery = Wrappers.lambdaQuery();
            processQuery.eq(AuditManageProcess::getConfigId, param.getId());
            processQuery.orderByAsc(AuditManageProcess::getSort);
            List<AuditManageProcess> processList = auditManageProcessService.list(processQuery);
            // 查用户
            List<Long> userIds = processList.stream().flatMap(e -> {
                if (CollectionUtils.isNotEmpty(e.getUserIds())) {
                    return e.getUserIds().stream();
                } else {
                    return Stream.empty();
                }
            }).collect(Collectors.toList());
            List<String> userNames = userService.selectUserListById(userIds)
                    .stream()
                    .map(SysUser::getNickName)
                    .collect(Collectors.toList());
            for (AuditManageProcess auditManageProcess : processList) {
                auditManageProcess.setUserNames(userNames);
            }
            byId.setProcessList(processList);
        }
        return JsonResult.success(byId);
    }

    /**
     * 创建
     *
     * @param dto
     * @return
     */
    @PostMapping("create")
    public JsonResult<Boolean> create(@RequestBody @Valid AuditManageConfigCreateDto dto) {
        LambdaQueryWrapper<AuditManageConfig> configQuery = Wrappers.lambdaQuery();
        configQuery.eq(AuditManageConfig::getName, dto.getName());
        AssertUtil.isNull(auditManageConfigService.getOne(configQuery), "已存在同名的审核流程！");
        AuditManageConfig mapped = BeanMapper.map(dto, AuditManageConfig.class);
        auditManageConfigService.save(mapped);
        int sort = 0;
        for (AuditManageProcess auditManageProcess : dto.getProcessList()) {
            auditManageProcess.setConfigId(mapped.getId());
            auditManageProcess.setSort(sort++);
        }
        auditManageProcessService.insertList(dto.getProcessList());
        return JsonResult.success();
    }

    /**
     * 更新
     *
     * @param dto
     * @return
     */
    @PostMapping("update")
    public JsonResult<Boolean> update(@RequestBody @Valid AuditManageConfigUpdateDto dto) {
        LambdaQueryWrapper<AuditManageConfig> configQuery = Wrappers.lambdaQuery();
        configQuery.eq(AuditManageConfig::getName, dto.getName());
        AuditManageConfig one = auditManageConfigService.getOne(configQuery);
        AssertUtil.isTrue(one == null || Objects.equals(one.getId(), dto.getId()), "审核名称重复，请重新修改！");
        if (one == null) {
            one = BeanMapper.map(dto, AuditManageConfig.class);
        }
        // 编辑账号时，需检查之前的审核
        // 找出未审批过的info对应的process
        List<AuditManageProcess> auditingProcessList = auditManageProcessService.auditingProcessList(dto.getId());
        Set<Long> mustContainProcessIds = auditingProcessList.stream()
                .map(AuditManageProcess::getId)
                .collect(Collectors.toSet());
        Set<Long> processIds = dto.getProcessList().stream().map(AuditManageProcess::getId).collect(Collectors.toSet());
        AssertUtil.isTrue(processIds.containsAll(mustContainProcessIds), "原账号存在未审核的数据");
        one.setName(dto.getName());
        auditManageConfigService.updateById(one);
        int sort = 0;
        for (AuditManageProcess auditManageProcess : dto.getProcessList()) {
            auditManageProcess.setConfigId(dto.getId());
            auditManageProcess.setSort(sort++);
        }
        auditManageProcessService.deleteByConfigId(dto.getId());
        auditManageProcessService.insertList(dto.getProcessList());
        return JsonResult.success();
    }

    @PostMapping("checkBeforeUpdate")
    public JsonResult<Void> checkBeforeUpdate(@RequestBody @Valid AuditManageConfigUpdateCheckDto dto) {
        LambdaQueryWrapper<AuditManageInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AuditManageInfo::getStatus, 0);
        queryWrapper.eq(AuditManageInfo::getProcessId, dto.getProcessId());
        int count = auditManageInfoService.count(queryWrapper);
        AssertUtil.isTrue(count == 0, "原账号存在未审核的数据");
        return JsonResult.success();
    }

    /**
     * 删除
     *
     * @param param
     * @return
     */
    @PostMapping("delete")
    public JsonResult<Boolean> delete(@RequestBody @Valid IdParam param) {
        LambdaQueryWrapper<AuditManageProcess> processQuery = Wrappers.lambdaQuery();
        processQuery.eq(AuditManageProcess::getConfigId, param.getId());
        List<AuditManageProcess> processList = auditManageProcessService.list(processQuery);
        if (CollectionUtils.isNotEmpty(processList)) {
            List<Long> processIds = processList.stream().map(AuditManageProcess::getId).collect(Collectors.toList());
            // 检查未审批完成的数据
            LambdaQueryWrapper<AuditManageInfo> infoQuery = Wrappers.lambdaQuery();
            infoQuery.in(AuditManageInfo::getProcessId, processIds);
            infoQuery.eq(AuditManageInfo::getStatus, 0);
            AssertUtil.isTrue(auditManageInfoService.count(infoQuery) == 0, "该审批流程存在未审核完成的数据，无法删除");
        }
        auditManageProcessService.deleteByConfigId(param.getId());
        return JsonResult.success(auditManageConfigService.removeById(param.getId()));
    }
}
