package com.yjtech.wisdom.tourism.portal.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.validate.CreateGroup;
import com.yjtech.wisdom.tourism.common.core.domain.validate.UpdateGroup;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.system.domain.Icon;
import com.yjtech.wisdom.tourism.system.service.IconService;
import com.yjtech.wisdom.tourism.system.vo.IconQuery;
import com.yjtech.wisdom.tourism.system.vo.IconVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 点位图标维护
 *
 * @author liuhong
 * @date 2021-07-02 15:43
 */
@RestController
@RequestMapping("/system/icon")
public class IconController {
    @Autowired
    private IconService iconService;

    /**
     * 查询点位图标列表, 支持点位类型查询
     *
     * @return 响应结果
     */
    @PostMapping("queryForPage")
    public JsonResult<IPage<Icon>> queryForPage(@RequestBody IconQuery params) {
        IPage<Icon> iconPage = iconService.queryForPage(params);
        return JsonResult.success(iconPage);
    }

    /**
     * 查询点位图标详情
     *
     * @param idParam id
     * @return 响应结果
     */
    @PostMapping("queryForDetail")
    public JsonResult<IconVO> queryForDetail(@RequestBody IdParam idParam) {
        Icon icon = iconService.queryForDetail(idParam.getId());
        return JsonResult.success(BeanMapper.map(icon, IconVO.class));
    }

    /**
     * 创建点位图标
     *
     * @param params 点位图标
     * @return 响应结果
     */
    @PreAuthorize("@ss.hasPermi('config:spotsImg:add')")
    @PostMapping("create")
    public JsonResult<Icon> create(@RequestBody @Validated(CreateGroup.class) IconVO params) {
        AssertUtil.isFalse(iconService.typeExists(params.getType(), null), "该点位类型已有记录");
        Icon icon = BeanMapper.map(params, Icon.class);
        iconService.saveIcon(icon);
        return JsonResult.success(icon);
    }

    /**
     * 编辑点位图标
     *
     * @param params 点位图标
     * @return 响应结果
     */
    @PreAuthorize("@ss.hasPermi('config:spotsImg:edit')")
    @PostMapping("update")
    public JsonResult<?> update(@RequestBody @Validated(UpdateGroup.class) IconVO params) {
        AssertUtil.isFalse(iconService.typeExists(params.getType(), params.getId()), "该点位类型已有记录");
        iconService.getIconById(params.getId());
        iconService.saveIcon(BeanMapper.map(params, Icon.class));
        return JsonResult.success();
    }

    /**
     * 删除点位图标
     *
     * @param idParam id
     * @return 响应结果
     */
    @PreAuthorize("@ss.hasPermi('config:spotsImg:remove')")
    @PostMapping("delete")
    public JsonResult<?> delete(@RequestBody @Validated IdParam idParam) {
        Icon icon = iconService.getIconById(idParam.getId());
        iconService.removeById(icon.getId());
        return JsonResult.success();
    }
}
