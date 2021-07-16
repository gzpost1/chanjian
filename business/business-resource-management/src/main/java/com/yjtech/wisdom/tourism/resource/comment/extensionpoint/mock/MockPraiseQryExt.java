package com.yjtech.wisdom.tourism.resource.comment.extensionpoint.mock;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.common.core.domain.PageQuery;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.resource.comment.dto.JsonData;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.CommentDayForCommentDate;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.CommentMonthForCommentDate;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.ScreenCommentTotalDto;
import com.yjtech.wisdom.tourism.resource.comment.extensionpoint.PraiseExtensionConstant;
import com.yjtech.wisdom.tourism.resource.comment.extensionpoint.PraiseQryExtPt;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;

/**
 * 票务模拟数据扩展点
 *
 * @author liuhong
 * @date 2021-07-13 11:12
 */
@Extension(bizId = ExtensionConstant.BIZ_PRAISE,
        useCase = PraiseExtensionConstant.USE_CASE_PRAISE_TYPE,
        scenario = ExtensionConstant.SCENARIO_MOCK)
public class MockPraiseQryExt implements PraiseQryExtPt {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 总评数
     *
     * @param
     * @return
     */
    @Override
    public ScreenCommentTotalDto queryForAll() {
        return getDataByRedis().getOne();
    }

    /**
     * 评论热度趋势/评论满意度趋势
     *
     * @param
     * @return
     */
    @Override
    public CommentDayForCommentDate queryDayForCommentDate() {
        return getDataByRedis().getTwo();
    }

    /**
     * 热度分布TOP5
     *
     * @param
     * @return
     */
    @Override
    public IPage<BaseVO> queryTotalOtaTop(PageQuery query) {
        Map<String, Object> map = (Map<String, Object>) getDataByRedis().getFour();
        if (map != null) {
            IPage<BaseVO> page = new Page<BaseVO>();
            List<BaseVO> list = (List<BaseVO>) map.get("redu");
            page.setRecords(list);
            page.setPages(1L);
            page.setTotal(list != null ? list.size() : 0L);
            return page;
        }
        return null;
    }

    /**
     * 满意度分布TOP5
     *
     * @param
     * @return
     */
    @Override
    public IPage<BasePercentVO> queryExcellentTotalOtaTop(PageQuery query) {
        Map<String, Object> map = (Map<String, Object>) getDataByRedis().getFour();
        if (map != null) {
            IPage<BasePercentVO> page = new Page<BasePercentVO>();
            List<BasePercentVO> list = (List<BasePercentVO>) map.get("manyidu");
            page.setRecords(list);
            page.setPages(1L);
            page.setTotal(list != null ? list.size() : 0L);
            return page;
        }
        return null;
    }

    /**
     * 近12月好评趋势/近12月差评趋势
     *
     * @return
     */
    @Override
    public CommentMonthForCommentDate queryMonthForCommentDate() {
        return getDataByRedis().getThree();
    }

    @SneakyThrows
    private JsonData getDataByRedis() {
        String json = (String) redisTemplate.opsForValue().get(Constants.SIMULATION_KEY + SimulationConstants.PRAISE);
        if (StringUtils.isNotBlank(json)) {

            return new ObjectMapper().readValue(json, JsonData.class);
        }
        return new JsonData();
    }
}
