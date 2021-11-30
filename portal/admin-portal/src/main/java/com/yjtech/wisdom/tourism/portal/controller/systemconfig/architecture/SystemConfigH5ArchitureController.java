package com.yjtech.wisdom.tourism.portal.controller.systemconfig.architecture;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.infrastructure.utils.TreeUtil;
import com.yjtech.wisdom.tourism.systemconfig.architecture.dto.*;
import com.yjtech.wisdom.tourism.systemconfig.architecture.entity.TbSystemconfigArchitectureEntity;
import com.yjtech.wisdom.tourism.systemconfig.architecture.service.TbSystemconfigArchitectureService;
import com.yjtech.wisdom.tourism.systemconfig.menu.service.SystemconfigMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 后台管理-系统配置-H5菜单架构
 */
@RestController
@RequestMapping("/systemconfig/h5/architeure")
public class SystemConfigH5ArchitureController {
    @Autowired
    private TbSystemconfigArchitectureService service;
    @Autowired
    private SystemconfigMenuService systemconfigMenuService;

    /**
     * 获取菜单树
     */
    @PostMapping("/getMenuTree")
    public JsonResult getMenuTree() {
        List<MenuTreeNode> treeNodeList = service.getAreaTree(1, Constants.TYPE_H5_SCREEN);
        List<MenuTreeNode> menuTreeNodes = TreeUtil.makeTree(treeNodeList);
        String pintaiName = service.getPintaiName();
        if (treeNodeList.size() == 0) {
            MenuTreeNode menuTreeNode = new MenuTreeNode();
            menuTreeNode.setId("1");
            menuTreeNode.setLabel(pintaiName);
            menuTreeNode.setTitle(pintaiName);
            menuTreeNodes.add(menuTreeNode);
        } else {
            menuTreeNodes.get(0).setLabel(pintaiName);
            menuTreeNodes.get(0).setTitle(pintaiName);
        }
        return JsonResult.success(menuTreeNodes);
    }

    /**
     * 分页 默认menuId为0 如果返回值中的pageId中的有值则为末节点否则为其他节点
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<SystemconfigArchitectureDto>> queryForPage(@RequestBody SystemconfigArchitecturePageQuery query) {
        query.setType(Constants.TYPE_H5_SCREEN);
        return JsonResult.success(service.queryForPage(query));
    }

    /**
     * 详情
     *
     * @param idParam
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult<SystemconfigArchitectureDto> queryForDetail(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(service.queryForDetail(idParam.getId()));
    }

    /**
     * 新增时校验,传入新增的父节点id,没有父级传入0
     *
     * @param
     * @return
     */
    @PostMapping("/createValidate")
    public JsonResult createValidate(@RequestBody @Valid IdParam idParam) {

        TbSystemconfigArchitectureEntity byId = service.getById(idParam.getId());
        //如果不是第一次添加
        if (Objects.nonNull(byId)) {
            //选择节点是否为最末级，若该菜单有页面关联，则认为是最末级，提示：该页面已是最末级，不可增加子节点。若无页面关联，显示新增弹窗
            if (Objects.nonNull(byId.getPageId())) {
                return JsonResult.error(ErrorCode.BUSINESS_EXCEPTION.code(), "该页面已是最末级，不可增加子节点！");
            }

            //包含根节点在内，当前新增层级是否超过第3级，若是，提示：最多只能创建二级菜单
            if (Objects.nonNull(byId.getFourId())) {
                return JsonResult.error(ErrorCode.BUSINESS_EXCEPTION.code(), "最多只能创建三级菜单！");
            }
        }

        return JsonResult.ok();
    }

    /**
     * 获取页面名称
     *
     * @param
     * @return
     */
    @PostMapping("/queryPageList")
    public JsonResult<List<BaseVO>> queryForDetail() {
        return JsonResult.success(systemconfigMenuService.queryPageList());
    }

    /**
     * 新增
     *
     * @param createDto
     * @return
     */
    @PostMapping("/create")
//    @PreAuthorize("@ss.hasPermi('h5:menu:add')")
    public JsonResult create(@RequestBody @Valid SystemconfigArchitectureCreateDto createDto) {
        createDto.setType(Constants.TYPE_H5_SCREEN);
        service.createH5(createDto);

        return JsonResult.ok();
    }

    /**
     * 编辑
     *
     * @param updateDto
     * @return
     */
    @PostMapping("/update")
//    @PreAuthorize("@ss.hasPermi('h5:menu:edit')")
    public JsonResult update(@RequestBody @Valid SystemconfigArchitectureUpdateDto updateDto) {

        Integer sortNum = service.getChildNumByParendId(updateDto.getMenuId());

        if (sortNum > 0 && Objects.nonNull(updateDto.getPageId())) {
            return JsonResult.error(ErrorCode.BUSINESS_EXCEPTION.code(), "该节点下有子节点，不可修改为页面");
        }

        TbSystemconfigArchitectureEntity entity = new TbSystemconfigArchitectureEntity();
        BeanUtils.copyProperties(updateDto, entity);

        service.updateById(entity);

        return JsonResult.ok();
    }

    /**
     * 删除
     *
     * @param idParam
     * @return
     * @todo
     */
    @PostMapping("/delete")
//    @PreAuthorize("@ss.hasPermi('h5:menu:remove')")
    public JsonResult delete(@RequestBody @Valid IdParam idParam) {
        Integer sortNum = service.getChildNumByParendId(idParam.getId());

        if (sortNum > 0) {
            return JsonResult.error(ErrorCode.BUSINESS_EXCEPTION.code(), "该节点下有子节点，不可删除");
        }

        service.removeById(idParam.getId());

        return JsonResult.ok();
    }

    /**
     * 更新模拟数据状态
     *
     * @param updateStatusParam
     * @return
     */
    @PostMapping("/updateSimulationStatus")
//    @PreAuthorize("@ss.hasPermi('h5:menu:isMock')")
    public JsonResult updateSimulationStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        service.updateSimulationStatus(updateStatusParam);
        return JsonResult.ok();
    }

    /**
     * 更新展示状态
     *
     * @param updateStatusParam
     * @return
     */
    @PostMapping("/updateShowStatus")
//    @PreAuthorize("@ss.hasPermi('h5:menu:isShow')")
    public JsonResult updateShowStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        service.updateShowStatus(updateStatusParam);
        return JsonResult.ok();
    }

    /**
     * 上移或者下移  0上移  1 下移
     *
     * @param updateStatusParam
     * @return
     */
    @PostMapping("/updateSortNum")
    public JsonResult updateSortNum(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        //查出当前对象
        TbSystemconfigArchitectureEntity byId = service.getById(updateStatusParam.getId());
        if (Objects.isNull(byId)) {
            return JsonResult.error(ErrorCode.BUSINESS_EXCEPTION.code(), "当前记录不存在，无法移动！");
        }

        //查出父级的最小序号和最大序号
        BaseVO baseVO = this.service.queryMaxAndMinByParendId(byId.getParentId());
        if (
            //最小值
                (StringUtils.equals(baseVO.getName(), String.valueOf(byId.getSortNum())) && StringUtils.equals("0", updateStatusParam.getStatus() + "")) ||
                        (StringUtils.equals(baseVO.getValue(), String.valueOf(byId.getSortNum())) && StringUtils.equals("1", updateStatusParam.getStatus() + ""))
                ) {
            return JsonResult.error(ErrorCode.BUSINESS_EXCEPTION.code(), "当前记录已经是最大或者最小记录，无法移动！");
        }

        //增加序号或者减小序号
        service.updateSortNum(updateStatusParam);
        return JsonResult.ok();
    }
}
