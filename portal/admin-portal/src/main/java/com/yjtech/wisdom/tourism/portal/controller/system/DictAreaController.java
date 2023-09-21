package com.yjtech.wisdom.tourism.portal.controller.system;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.dto.area.AreaTreeNode;
import com.yjtech.wisdom.tourism.infrastructure.utils.TreeUtil;
import com.yjtech.wisdom.tourism.position.service.TbDictAreaService;
import com.yjtech.wisdom.tourism.system.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author songjun
 * @since 2023/9/11
 */
@RestController
@RequestMapping("/system/dictarea")
public class DictAreaController {

    @Autowired
    private TbDictAreaService areaService;

    /**
     * 获得区域树
     */
    @PostMapping("/getAreaTree")
    public JsonResult getAreaTree() {
        List<AreaTreeNode> treeNodeList = areaService.getAreaTree(null);
        return JsonResult.success(TreeUtil.makeTree(treeNodeList));
    }

}
