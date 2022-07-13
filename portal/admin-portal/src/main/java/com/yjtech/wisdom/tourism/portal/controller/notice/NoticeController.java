package com.yjtech.wisdom.tourism.portal.controller.notice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.DeleteParam;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.resource.notice.entity.NoticeEntity;
import com.yjtech.wisdom.tourism.resource.notice.service.NoticeService;
import com.yjtech.wisdom.tourism.resource.notice.vo.NoticeCreateVO;
import com.yjtech.wisdom.tourism.resource.notice.vo.NoticeQueryVO;
import com.yjtech.wisdom.tourism.resource.notice.vo.NoticeUpdateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 管理后台-公告管理
 *
 * @author horadirm
 * @date 2022/5/24 11:46
 */
@RestController
@RequestMapping("/notice/")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;


    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @PostMapping("create")
    public JsonResult create(@RequestBody @Valid NoticeCreateVO vo) {
        noticeService.create(vo);
        return JsonResult.success();
    }

    /**
     * 编辑
     *
     * @param vo
     * @return
     */
    @PostMapping("update")
    public JsonResult update(@RequestBody @Valid NoticeUpdateVO vo) {
        noticeService.update(vo);
        return JsonResult.success();
    }

    /**
     * 更新状态
     *
     * @param updateStatusParam
     * @return
     */
    @PostMapping("updateStatus")
    public JsonResult updateStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        noticeService.updateStatus(updateStatusParam);
        return JsonResult.success();
    }

    /**
     * 根据id查询信息
     *
     * @param idParam
     * @return
     */
    @PostMapping("queryForId")
    public JsonResult<NoticeEntity> queryForId(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(noticeService.getBaseMapper().selectById(idParam.getId()));
    }

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForList")
    public JsonResult<List<NoticeEntity>> queryForList(@RequestBody @Valid NoticeQueryVO vo) {
        return JsonResult.success(noticeService.queryForAdminList(vo));
    }

    /**
     * 查询分页
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForPage")
    public JsonResult<IPage<NoticeEntity>> queryForPage(@RequestBody @Valid NoticeQueryVO vo) {
        return JsonResult.success(noticeService.queryForAdminPage(vo));
    }

    /**
     * 删除
     *
     * @param deleteParam
     * @return
     */
    @PostMapping("delete")
    public JsonResult delete(@RequestBody @Valid DeleteParam deleteParam) {
        noticeService.delete(deleteParam.getId());
        return JsonResult.success();
    }

}
