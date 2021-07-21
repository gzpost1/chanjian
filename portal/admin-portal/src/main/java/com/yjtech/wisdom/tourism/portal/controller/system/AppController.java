package com.yjtech.wisdom.tourism.portal.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.PageQuery;
import com.yjtech.wisdom.tourism.common.core.domain.validate.CreateGroup;
import com.yjtech.wisdom.tourism.common.core.domain.validate.UpdateGroup;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.system.domain.App;
import com.yjtech.wisdom.tourism.system.service.AppService;
import com.yjtech.wisdom.tourism.system.vo.AppVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统管理-app下载
 *
 * @author liuhong
 * @date 2021-07-02 15:43
 */
@RestController
@RequestMapping("/system/app")
public class AppController {
    @Autowired
    private AppService appService;

    /**
     * 查询app下载列表
     *
     * @return 响应结果
     */
    @PostMapping("queryForPage")
    public JsonResult<IPage<App>> queryForPage(@RequestBody PageQuery params) {
        IPage<App> appIPage = appService.page(new Page<>(params.getPageNo(), params.getPageSize()));
        return JsonResult.success(appIPage);
    }

    /**
     * 查询app下载详情
     */
    @PostMapping("queryForDetail")
    public JsonResult<AppVO> queryForDetail(@RequestBody IdParam idParam) {
        App app = appService.getAppById(idParam.getId());
        return JsonResult.success(BeanMapper.map(app, AppVO.class));
    }

    /**
     * 创建app下载
     */
    @PostMapping("create")
    public JsonResult<App> create(@RequestBody @Validated(CreateGroup.class) AppVO params) {
        AssertUtil.isFalse(appService.versionExists(params.getVersion(), null), "版本号已存在");
        App app = BeanMapper.map(params, App.class);
        appService.save(app);
        return JsonResult.success(app);
    }

    /**
     * 编辑app下载
     */
    @PostMapping("update")
    public JsonResult<?> update(@RequestBody @Validated(UpdateGroup.class) AppVO params) {
        AssertUtil.isFalse(appService.versionExists(params.getVersion(), params.getId()), "版本号已存在");
        appService.getAppById(params.getId());
        appService.updateById(BeanMapper.map(params, App.class));
        return JsonResult.success();
    }

    /**
     * 删除app下载
     */
    @PostMapping("delete")
    public JsonResult<?> delete(@RequestBody @Validated IdParam idParam) {
        App icon = appService.getAppById(idParam.getId());
        appService.removeById(icon.getId());
        return JsonResult.success();
    }
}
