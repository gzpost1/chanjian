package com.yjtech.wisdom.tourism.portal.controller.lecture;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.resource.lecture.dto.LectureDto;
import com.yjtech.wisdom.tourism.resource.lecture.dto.LecturePicDto;
import com.yjtech.wisdom.tourism.resource.lecture.service.LectureMangerService;
import com.yjtech.wisdom.tourism.resource.lecture.vo.LecturePageByVenueIdVo;
import com.yjtech.wisdom.tourism.resource.lecture.vo.LectureScaleVo;
import com.yjtech.wisdom.tourism.resource.lecture.vo.LectureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 大屏_展演讲座管理
 *
 * @author renguangqian
 * @date 2021/7/21 19:50
 */
@RestController
@RequestMapping("screen/lecture")
public class LectureMangerController {

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

    /**
     * 根据场馆id查询讲座信息_分页
     *
     * @param vo
     * @return
     */
    @PostMapping("/queryLecturePageByVenueId")
    private JsonResult<IPage<LectureDto>> queryLecturePageByVenueId (@RequestBody @Validated LecturePageByVenueIdVo vo) {
        return JsonResult.success(lectureMangerService.queryLecturePageByVenueId(vo));
    }

    /**
     * 查询展演讲座分布比列
     *
     * @return
     */
    @PostMapping("queryScale")
    private JsonResult<LecturePicDto> queryScale (@RequestBody @Validated LectureScaleVo vo) {
        return JsonResult.success(lectureMangerService.queryScale(vo));
    }
}
