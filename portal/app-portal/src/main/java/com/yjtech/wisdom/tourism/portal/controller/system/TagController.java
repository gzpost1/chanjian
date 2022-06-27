package com.yjtech.wisdom.tourism.portal.controller.system;

import com.yjtech.wisdom.tourism.common.annotation.IgnoreAuth;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.system.domain.TagEntity;
import com.yjtech.wisdom.tourism.system.service.TagService;
import com.yjtech.wisdom.tourism.system.vo.TagQueryVO;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 大屏-标签管理
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

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForList")
    public JsonResult<List<TagEntity>> queryForList(@RequestBody @Valid TagQueryVO vo) {
        return JsonResult.success(tagService.queryForList(vo));
    }

}
