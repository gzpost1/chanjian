package com.yjtech.wisdom.tourism.portal.controller.comment;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.extension.ExtensionExecutor;
import com.yjtech.wisdom.tourism.resource.comment.dto.PraiseCommentDto;
import com.yjtech.wisdom.tourism.resource.comment.dto.PraiseCommentPageQueryDto;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.*;
import com.yjtech.wisdom.tourism.resource.comment.extensionpoint.PraiseExtensionConstant;
import com.yjtech.wisdom.tourism.resource.comment.extensionpoint.PraiseQryExtPt;
import com.yjtech.wisdom.tourism.resource.comment.extensionpoint.PraiseWordQryExtPt;
import com.yjtech.wisdom.tourism.resource.comment.service.PraiseCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 大屏-口碑
 *
 * @author libo
 * @since 2021-07-02
 */
@RestController
@RequestMapping("/screen/comment")
public class CommentController {

    @Autowired
    private PraiseCommentService praiseCommentService;
    @Resource
    private ExtensionExecutor extensionExecutor;

    /**
     * 分页
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<PraiseCommentDto>> queryForPage(@RequestBody PraiseCommentPageQueryDto query) {
        return JsonResult.success(praiseCommentService.queryForPage(query));
    }

    /**
     * 总评数
     *
     * @param
     * @return
     */
    @PostMapping("/queryForAll")
    public JsonResult<ScreenCommentTotalDto> queryForAll(@RequestBody PraiseCommonQuery query) {
        return JsonResult.success(
                extensionExecutor.execute(PraiseQryExtPt.class,
                        buildBizScenario(PraiseExtensionConstant.USE_CASE_PRAISE_TYPE, query.getIsSimulation()),
                        PraiseQryExtPt::queryForAll));
    }

    /**
     * 评论热度趋势
     *
     * @param
     * @return
     */
    @PostMapping("/queryMonthAllList")
    public JsonResult<List<BaseVO>> queryMonthAllList(@RequestBody PraiseCommonQuery query) {
        CommentDayForCommentDate execute = extensionExecutor.execute(PraiseQryExtPt.class,
                buildBizScenario(PraiseExtensionConstant.USE_CASE_PRAISE_TYPE, query.getIsSimulation()),
                PraiseQryExtPt::queryDayForCommentDate);
        List<BaseVO> list = new ArrayList<>();
        if(Objects.nonNull(execute)){
            list = execute.getTotalList();
        }

        return JsonResult.success(list);
    }

    /**
     * 评论满意度趋势
     *
     * @param
     * @return
     */
    @PostMapping("/queryMonthSatisfactionList")
    public JsonResult<List<BasePercentVO>> queryMonthSatisfactionList(@RequestBody PraiseCommonQuery query) {
        CommentDayForCommentDate execute = extensionExecutor.execute(PraiseQryExtPt.class,
                buildBizScenario(PraiseExtensionConstant.USE_CASE_PRAISE_TYPE, query.getIsSimulation()),
                PraiseQryExtPt::queryDayForCommentDate);
        List<BasePercentVO> list = new ArrayList<>();
        if(Objects.nonNull(execute)){
            list = execute.getExcellentList();
        }

        return JsonResult.success(list);
    }


    /**
     * 热度分布TOP5
     *
     * @param
     * @return
     */
    @PostMapping("/queryTotalOtaTop")
    public JsonResult<IPage<BaseVO>> queryTotalOtaTop(@RequestBody PraiseCommonPageQuery query) {
        return JsonResult.success(
                extensionExecutor.execute(PraiseQryExtPt.class,
                        buildBizScenario(PraiseExtensionConstant.USE_CASE_PRAISE_TYPE, query.getIsSimulation()),
                        exit -> exit.queryTotalOtaTop(query))
        );
    }

    /**
     * 满意度分布TOP5
     *
     * @param
     * @return
     */
    @PostMapping("/queryExcellentTotalOtaTop")
    public JsonResult<IPage<BasePercentVO>> queryExcellentTotalOtaTop(@RequestBody PraiseCommonPageQuery query) {
        return JsonResult.success(
                extensionExecutor.execute(PraiseQryExtPt.class,
                        buildBizScenario(PraiseExtensionConstant.USE_CASE_PRAISE_TYPE, query.getIsSimulation()),
                        exit -> exit.queryExcellentTotalOtaTop(query))
        );
    }

    /**
     * 近12月好评趋势
     *
     * @param
     * @return
     */
    @PostMapping("/queryMonthForSatisfactionCommentDate")
    public JsonResult<List<BaseVO>> queryMonthForSatisfactionCommentDate(@RequestBody PraiseCommonQuery query) {
        CommentMonthForCommentDate execute = extensionExecutor.execute(PraiseQryExtPt.class,
                buildBizScenario(PraiseExtensionConstant.USE_CASE_PRAISE_TYPE, query.getIsSimulation()),
                exit -> exit.queryMonthForCommentDate());
        List<BaseVO> list = new ArrayList<>();
        if(Objects.nonNull(execute)){
            list = execute.getExcellentList();
        }
        return JsonResult.success(list);
    }

    /**
     * 近12月差评趋势
     *
     * @param
     * @return
     */
    @PostMapping("/queryMonthForPoorCommentDate")
    public JsonResult<List<BaseVO>> queryMonthForPoorCommentDate(@RequestBody PraiseCommonQuery query) {
        CommentMonthForCommentDate execute = extensionExecutor.execute(PraiseQryExtPt.class,
                buildBizScenario(PraiseExtensionConstant.USE_CASE_PRAISE_TYPE, query.getIsSimulation()),
                exit -> exit.queryMonthForCommentDate());
        List<BaseVO> list = new ArrayList<>();
        if(Objects.nonNull(execute)){
            list = execute.getPoorList();
        }
        return JsonResult.success(list);
    }

    /**
     * 行业评价分布
     *
     * @param
     * @return
     */
    @PostMapping("/queryIndustryBYComments")
    public JsonResult<List<BaseVO>> queryIndustryBYComments(@RequestBody PraiseCommonQuery query) {
        Map<String, Object> execute = extensionExecutor.execute(PraiseWordQryExtPt.class,
                buildBizScenario(PraiseExtensionConstant.USE_CASE_PRAISE_WORD, query.getIsSimulation()),
                exit -> exit.queryHotWordBYComments());
        if(execute != null){
            return JsonResult.success((List<BaseVO>)execute.get("hangye"));
        }
        return JsonResult.success(new ArrayList<BaseVO>());
    }

    /**
     * 评论热词
     *
     * @param
     * @return
     */
    @PostMapping("/queryHotWordBYComments")
    public JsonResult<List<BaseVO>> queryHotWordBYComments(@RequestBody PraiseCommonQuery query) {
        Map<String, Object> execute = extensionExecutor.execute(PraiseWordQryExtPt.class,
                buildBizScenario(PraiseExtensionConstant.USE_CASE_PRAISE_WORD, query.getIsSimulation()),
                exit -> exit.queryHotWordBYComments());
        if(execute != null){
            return JsonResult.success((List<BaseVO>)execute.get("hotword"));
        }
        return JsonResult.success(new ArrayList<BaseVO>());
    }

    private BizScenario buildBizScenario(String useCasePraiseType, Integer isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.BIZ_PRAISE, useCasePraiseType
                , isSimulation == 0 ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }
}
