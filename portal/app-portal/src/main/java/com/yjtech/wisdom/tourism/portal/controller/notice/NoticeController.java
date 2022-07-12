package com.yjtech.wisdom.tourism.portal.controller.notice;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.annotation.IgnoreAuth;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.CompanyRoleEnum;
import com.yjtech.wisdom.tourism.framework.web.service.ScreenTokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.ScreenLoginUser;
import com.yjtech.wisdom.tourism.project.service.TbProjectInfoService;
import com.yjtech.wisdom.tourism.resource.notice.dto.NoticeScreenScrollDTO;
import com.yjtech.wisdom.tourism.resource.notice.service.NoticeService;
import com.yjtech.wisdom.tourism.resource.notice.vo.NoticeQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 大屏-公告管理
 *
 * @author horadirm
 * @date 2022/5/24 11:46
 */
@Slf4j
@RestController
@RequestMapping("/screen/notice/")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private TbProjectInfoService tbProjectInfoService;
    @Autowired
    private ScreenTokenService screenTokenService;


    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    @IgnoreAuth
    @PostMapping("queryForList")
    public JsonResult<List<NoticeScreenScrollDTO>> queryForList(@RequestBody @Valid NoticeQueryVO vo, HttpServletRequest request) {
        vo.setBusinessIdList(buildProjectIdListByCompanyId(request));
        return JsonResult.success(noticeService.queryForScreenList(vo));
    }

    /**
     * 查询分页
     *
     * @param vo
     * @return
     */
    @IgnoreAuth
    @PostMapping("queryForPage")
    public JsonResult<IPage<NoticeScreenScrollDTO>> queryForPage(@RequestBody @Valid NoticeQueryVO vo, HttpServletRequest request) {
        vo.setBusinessIdList(buildProjectIdListByCompanyId(request));
        return JsonResult.success(noticeService.queryForScreenPage(vo));
    }

    /**
     * 更新消息已读
     *
     * @param idParam
     * @return
     */
    @PostMapping("updateNoticeRead")
    public JsonResult updateNoticeRead(@RequestBody @Valid IdParam idParam) {
        noticeService.updateNoticeRead(idParam.getId());
        return JsonResult.success();
    }

    /**
     * 根据企业id构建项目id列表
     *
     * @param request
     * @return
     */
    private List<String> buildProjectIdListByCompanyId(HttpServletRequest request){
        ScreenLoginUser loginUser = screenTokenService.getLoginUser(request);
        log.warn("******************** 当前登录用户信息：{}", JSONObject.toJSONString(loginUser));
        //登录用户为空，则返回为空，查询范围为公告
        if(null == loginUser){
            return null;
        }
        //登录企业用户角色为项目方时，查询范围为公告、消息
        if(CollectionUtils.isNotEmpty(loginUser.getType()) && loginUser.getType().contains(CompanyRoleEnum.COMPANY_ROLE_PROJECT.getType())){
            //根据企业id查询项目id列表
            List<Long> projectIdList = tbProjectInfoService.queryIdListByCompanyId(loginUser.getId());
            if(CollectionUtils.isNotEmpty(projectIdList)){
                return projectIdList.stream().map(String::valueOf).collect(Collectors.toList());
            }
        }
        return null;
    }


}
