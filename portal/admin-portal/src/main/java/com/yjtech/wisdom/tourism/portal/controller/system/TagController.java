package com.yjtech.wisdom.tourism.portal.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.annotation.IgnoreAuth;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.system.domain.TagEntity;
import com.yjtech.wisdom.tourism.system.service.TagService;
import com.yjtech.wisdom.tourism.system.vo.TagQueryVO;
import com.yjtech.wisdom.tourism.system.vo.TagUpdateVO;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 管理后台-标签管理
 *
 * @date 2022/3/11 11:30
 * @author horadirm
 */
@RestController
@RequestMapping("/tag/")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 编辑
     *
     * @param vo
     * @return
     */
    @PostMapping("update")
    public JsonResult update(@RequestBody @Valid TagUpdateVO vo) {
        tagService.update(vo);
        return JsonResult.success();
    }

    /**
     * 根据id查询信息
     *
     * @param vo
     * @return
     */
    @PostMapping("queryById")
    public JsonResult<TagEntity> queryById(@RequestBody @Valid IdParam vo) {
        return JsonResult.success(tagService.queryById(vo.getId()));
    }

    /**
     * 查询分页
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForPage")
    public JsonResult<Page<TagEntity>> queryForPage(@RequestBody @Valid TagQueryVO vo) {
        return JsonResult.success(tagService.queryForPage(vo));
    }

    /**
     * 根据角色查询标签信息
     *
     * @param vo
     * @return
     */
    @IgnoreAuth
    @PostMapping("queryByRole")
    public JsonResult<TagEntity> queryByRole(@RequestBody @Valid TagQueryVO vo) {
        List<TagEntity> entityList = tagService.queryForList(vo);
        Assert.notEmpty(entityList, "标签信息不存在");

        return JsonResult.success(entityList.get(0));
    }
}
