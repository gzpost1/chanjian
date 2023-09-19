package com.yjtech.wisdom.tourism.portal.controller.talents;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.annotation.IgnoreAuth;
import com.yjtech.wisdom.tourism.common.core.domain.DeleteParam;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.resource.talents.entity.TalentsPoolEntity;
import com.yjtech.wisdom.tourism.resource.talents.service.TalentsPoolService;
import com.yjtech.wisdom.tourism.resource.talents.vo.TalentsPoolCreateVO;
import com.yjtech.wisdom.tourism.resource.talents.vo.TalentsPoolQueryVO;
import com.yjtech.wisdom.tourism.resource.talents.vo.TalentsPoolUpdateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 大屏-人才库管理
 *
 * @author horadirm
 * @date 2022/5/24 11:46
 */
@RestController
@RequestMapping("/screen/talentsPool/")
public class TalentsPoolController {

    @Autowired
    private TalentsPoolService talentsPoolService;

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForList")
    @IgnoreAuth
    public JsonResult<List<TalentsPoolEntity>> queryForList(@RequestBody @Valid TalentsPoolQueryVO vo) {
        vo.setHasAllRights(true);
        return JsonResult.success(talentsPoolService.queryForList(vo));
    }

    /**
     * 查询分页
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForPage")
    @IgnoreAuth
    public JsonResult<IPage<TalentsPoolEntity>> queryForPage(@RequestBody @Valid TalentsPoolQueryVO vo) {
        vo.setHasAllRights(true);
        return JsonResult.success(talentsPoolService.queryForPage(vo));
    }

}
