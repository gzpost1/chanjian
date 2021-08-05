package com.yjtech.wisdom.tourism.portal.controller.position;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.position.domain.DictAreaQuery;
import com.yjtech.wisdom.tourism.position.domain.DictAreaTree;
import com.yjtech.wisdom.tourism.position.service.TbDictAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 区域信息
 *
 * @Author horadirm
 * @Date 2021/8/4 11:13
 */
@RestController
@RequestMapping("/position/")
public class PositionController {
    @Autowired
    TbDictAreaService dictAreaService;

    /**
     * 获取地区树结构
     * @param dictAreaQuery
     * @return
     */
    @PostMapping("getDictAreaTree")
    public JsonResult<List<DictAreaTree>> getDictAreaTree(@RequestBody @Valid DictAreaQuery dictAreaQuery) {
        return JsonResult.success(dictAreaService.getDictAreaTree(dictAreaQuery.getCode(), dictAreaQuery.getLevel()));
    }

}
