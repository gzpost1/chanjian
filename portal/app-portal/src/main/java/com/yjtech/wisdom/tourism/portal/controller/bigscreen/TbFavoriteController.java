package com.yjtech.wisdom.tourism.portal.controller.bigscreen;


import com.yjtech.wisdom.tourism.bigscreen.dto.FavoriteParam;
import com.yjtech.wisdom.tourism.bigscreen.dto.MyFavoritesVo;
import com.yjtech.wisdom.tourism.bigscreen.dto.TbFavoriteParam;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbFavoriteEntity;
import com.yjtech.wisdom.tourism.bigscreen.service.TbFavoriteService;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.framework.web.service.ScreenTokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.ScreenLoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 企业的收藏
 *
 * @author Mujun
 * @since 2022-03-08
 */
@Slf4j
@RestController
@RequestMapping("/screen/favorite")
public class TbFavoriteController extends BaseCurdController<TbFavoriteService, TbFavoriteEntity, TbFavoriteParam> {

    @Autowired
    ScreenTokenService tokenService;

    @PostMapping("collect")
    public JsonResult collect(@RequestBody @Validated FavoriteParam param) {
        ScreenLoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        boolean exist = service.checkExist(loginUser.getId(), param.getFavoriteId(), param.getType());
        AssertUtil.isFalse(exist,"不能重复收藏相同内容");
        TbFavoriteEntity favoriteEntity = new TbFavoriteEntity();
        BeanUtils.copyProperties(param, favoriteEntity);
        favoriteEntity.setCompanyId(loginUser.getId());
        return JsonResult.success(service.save(favoriteEntity));
    }

    @PostMapping("myFavorites")
    public JsonResult<List<MyFavoritesVo>> myFavorites() {
        ScreenLoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return JsonResult.success(service.myFavorites(loginUser.getId()));
    }


}
