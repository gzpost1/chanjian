package com.yjtech.wisdom.tourism.portal.controller.system;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.dto.area.AreaInfoVO;
import com.yjtech.wisdom.tourism.dto.area.AreaTreeNode;
import com.yjtech.wisdom.tourism.infrastructure.utils.TreeUtil;
import com.yjtech.wisdom.tourism.system.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 区域信息
 *
 * @author wuyongchong
 * @date 2019/9/23
 */
@RestController
@RequestMapping("/system/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    /**
     * 获得区域树
     */
    @PostMapping("/getAreaTree")
    public JsonResult getAreaTree() {
        List<AreaTreeNode> treeNodeList = areaService.getAreaTree(null);
        return JsonResult.success(TreeUtil.makeTree(treeNodeList));
    }


    /**
     * 获得省级列表
     */
    @PostMapping("/getPrefectureLevelProvinceList")
    public JsonResult<List<AreaInfoVO>> getPrefectureLevelProvinceList() {
        return JsonResult.success(areaService.getPrefectureLevelProvinceList());
    }

    /**
     * 根据省获得下级市列表
     */
    @PostMapping("/getPrefectureLevelCityList/{provinceCode}")
    public JsonResult<List<AreaInfoVO>> getPrefectureLevelCityList(@PathVariable("provinceCode") String provinceCode) {
        return JsonResult.success(areaService.getPrefectureLevelCityList(provinceCode));
    }

}
