package com.yjtech.wisdom.tourism.portal.controller.lecture;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.resource.lecture.dto.LectureDto;
import com.yjtech.wisdom.tourism.resource.lecture.entity.LectureEntity;
import com.yjtech.wisdom.tourism.resource.lecture.service.LectureMangerService;
import com.yjtech.wisdom.tourism.resource.lecture.vo.LectureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台管理_展演讲座管理
 *
 * @author renguangqian
 * @date 2021/7/21 19:50
 */
@RestController
@RequestMapping("lecture")
public class LectureMangerController extends BaseCurdController<LectureMangerService, LectureEntity, LectureVo> {

    @Autowired
    private LectureMangerService lectureMangerService;

    /**
     * 查询展演讲座列表_分页
     *
     * @param vo
     * @return
     */
    @PostMapping("/queryPage")
    private JsonResult<IPage<LectureDto>> queryPage (@RequestBody @Validated LectureVo vo) {
        return JsonResult.success(lectureMangerService.queryPage(vo));
    }
}
