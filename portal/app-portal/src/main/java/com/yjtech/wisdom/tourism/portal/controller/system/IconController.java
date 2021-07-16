package com.yjtech.wisdom.tourism.portal.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.system.domain.Icon;
import com.yjtech.wisdom.tourism.system.service.IconService;
import com.yjtech.wisdom.tourism.system.vo.IconQuery;
import com.yjtech.wisdom.tourism.system.vo.IconVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 点位图标
 *
 * @author liuhong
 * @date 2021-07-02 15:43
 */
@RestController
@RequestMapping("/system/icon")
public class IconController {
    @Autowired
    private IconService iconService;

    /**
     * 查询点位图标列表, 支持点位类型查询
     *
     * @return 响应结果
     */
    @PostMapping("queryForPage")
    public JsonResult<IPage<Icon>> queryForPage(@RequestBody IconQuery params) {
        IPage<Icon> iconPage = iconService.queryForPage(params);
        return JsonResult.success(iconPage);
    }

    /**
     * 查询点位图标详情
     *
     * @param idParam id
     * @return 响应结果
     */
    @PostMapping("queryForDetail")
    public JsonResult<IconVO> queryForDetail(@RequestBody IdParam idParam) {
        Icon icon = iconService.queryForDetail(idParam.getId());
        return JsonResult.success(BeanMapper.map(icon, IconVO.class));
    }

}
