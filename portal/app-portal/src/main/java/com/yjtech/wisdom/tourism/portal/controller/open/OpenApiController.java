package com.yjtech.wisdom.tourism.portal.controller.open;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.bigscreen.dto.TbRegisterInfoParam;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.service.TbRegisterInfoService;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.position.entity.TbDictAreaEntity;
import com.yjtech.wisdom.tourism.position.service.TbDictAreaService;
import com.yjtech.wisdom.tourism.project.dto.OpenProjectInfoParam;
import com.yjtech.wisdom.tourism.project.entity.OpenProjectInfoEntity;
import com.yjtech.wisdom.tourism.project.service.OpenProjectInfoService;
import com.yjtech.wisdom.tourism.system.mapper.SysDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 对外提供项目数据以及客商数据接口
 */
@RestController
@RequestMapping("/open-api/")
public class OpenApiController {
    @Autowired
    OpenProjectInfoService projectInfoService;
    @Autowired
    TbRegisterInfoService registerInfoService;
    @Autowired
    private TbDictAreaService dictAreaService;
    @Autowired
    SysDictDataMapper dictDataMapper;
    /**
     * 项目数据 分页查询
     * @param params
     * @return
     */
    @PostMapping("/project/queryForPage")
    public JsonResult<Page<OpenProjectInfoEntity>> projectQueryForPage(
            @RequestBody OpenProjectInfoParam params) {
        return JsonResult.success(projectInfoService.page(params));
    }
    /**
     * 客商数据 分页查询
     * @param params
     * @return
     */
    @PostMapping("/registerInfo/queryForPage")
    public JsonResult<Page<TbRegisterInfoEntity>> registerInfoQueryForPage(
            @RequestBody TbRegisterInfoParam params) {
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
                List<String> roleType = record.getType();
                List<SysDictData> companyRoles = dictDataMapper.selectDictDataByType("company_role");
                List<String> roleName = companyRoles.stream().filter(c -> roleType.contains(c.getDictValue())).map(SysDictData::getDictLabel).collect(Collectors.toList());
                record.setRoleName(roleName);
            }
        }
        return JsonResult.success(page);
    }

}
