package com.yjtech.wisdom.tourism.portal.controller.lecture;

import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.resource.lecture.entity.LectureEntity;
import com.yjtech.wisdom.tourism.resource.lecture.service.LectureMangerService;
import com.yjtech.wisdom.tourism.resource.lecture.vo.LectureVo;
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
}
