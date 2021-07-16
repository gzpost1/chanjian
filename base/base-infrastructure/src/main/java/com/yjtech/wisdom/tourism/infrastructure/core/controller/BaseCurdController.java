package com.yjtech.wisdom.tourism.infrastructure.core.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.IdsParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.enums.BeanValidationGroup;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.mybatis.base.BaseService;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @Description
 * @Author Mujun~
 * @Date 2020-09-27 10:28
 */

@RestController
@Slf4j
public abstract class BaseCurdController<S extends BaseService<T>,T,P extends PageQuery> extends BaseController {

    @Autowired
    protected S service;

    /**
     * 分页查询
     * @param params
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<Page<T>> queryForPage(
            @RequestBody  P params) {
        return JsonResult.success(service.page(params));
    }

    /**
     * 详细信息查询
     * @param idParam
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult<T> queryForDetail(
            @RequestBody @Valid IdParam idParam) {
       T t = (T) service.getById(idParam.getId());
        return JsonResult.success(t);
    }
    /**
     * 新增
     * @param entity
     * @return
     */
    @PostMapping("/create")
    public JsonResult  create(
            @RequestBody @Valid T entity) {
        return JsonResult.success(service.save(entity));
    }
    /**
     * 单条删除
     * @param params
     * @return
     */
    @PostMapping("/delete")
    public JsonResult  delete(
            @RequestBody @Valid IdParam params) {
        return JsonResult.success(service.removeById(params.getId()));
    }

    /**
     * 单个或批量删除
     * @return
     */
    @PostMapping("/batchDelete")
    public JsonResult  batchDelete(
            @RequestBody @Valid IdsParam ids) {
        List<Long> idArrays  = ids.getIds();
        return JsonResult.success(service.removeByIds(idArrays));
    }

    /**
     * 更新信息
     * @param entity
     * @return
     */
    @PostMapping("/update")
    public JsonResult  update(
            @RequestBody @Validated(BeanValidationGroup.Update.class)  T entity) {
        return JsonResult.success(service.updateById(entity));
    }

    /**
     * 更新状态
     * @param params
     * @return
     */
    @PostMapping("/updateStatus")
    public JsonResult  updateStatus(
            @RequestBody @Valid UpdateStatusParam params) {
        T entity = BeanMapper.copyBean(params, getTClass());
        return JsonResult.success(service.updateById(entity));
    }

    public Class<T> getTClass() {
        return (Class<T>)
                ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
    /**
     * 列表
     * @param params
     * @return
     */
    @PostMapping("/list")
    public JsonResult<List<T>> list(
            @RequestBody  T params) {
        return JsonResult.success(service.list(params));
    }
}
