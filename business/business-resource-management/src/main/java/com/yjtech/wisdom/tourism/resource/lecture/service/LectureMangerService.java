package com.yjtech.wisdom.tourism.resource.lecture.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisServiceImpl;
import com.yjtech.wisdom.tourism.resource.lecture.dto.LectureDto;
import com.yjtech.wisdom.tourism.resource.lecture.dto.LecturePicDto;
import com.yjtech.wisdom.tourism.resource.lecture.dto.LectureScaleDto;
import com.yjtech.wisdom.tourism.resource.lecture.entity.LectureEntity;
import com.yjtech.wisdom.tourism.resource.lecture.mapper.LectureMapper;
import com.yjtech.wisdom.tourism.resource.lecture.vo.LecturePageByVenueIdVo;
import com.yjtech.wisdom.tourism.resource.lecture.vo.LectureScaleVo;
import com.yjtech.wisdom.tourism.resource.lecture.vo.LectureVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 展演讲座管理
 *
 * @author renguangqian
 * @date 2021/7/21 11:25
 */
@Service
public class LectureMangerService extends BaseMybatisServiceImpl<LectureMapper, LectureEntity> {

    /**
     * 查询展演讲座列表_分页
     *
     * @param vo
     * @return
     */
    public IPage<LectureDto> queryPage(LectureVo vo) {
        return baseMapper.selectPage(
                new Page<>(vo.getPageNo(), vo.getPageSize()),
                new LambdaQueryWrapper<LectureEntity>()
                    .eq(LectureEntity::getStatus, 1)
                    .like(!StringUtils.isEmpty(vo.getLectureName()), LectureEntity::getLectureName, vo.getLectureName())
                    .orderByDesc(LectureEntity::getUpdateTime))
        .convert(item -> JSONObject.parseObject(JSONObject.toJSONString(item), LectureDto.class));
    }

    /**
     * 查询展演讲座分布比列
     *
     * @param vo
     * @return
     */
    public LecturePicDto queryScale(LectureScaleVo vo) {
        QueryWrapper<LectureEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("select count(id) as lectureTypeNumber, lectureType");
        queryWrapper.eq("status", 1);
        queryWrapper.between("holdStartDate", vo.getBeginTime(), vo.getEndTime());
        queryWrapper.groupBy("venueType");

        // 符合条件的讲座类型 的数量
        List<LectureEntity> lectureEntityList = baseMapper.selectList(queryWrapper);

        // 总数
        int total = lectureEntityList.stream().mapToInt(LectureEntity::getLectureTypeNumber).sum();

        List<LectureScaleDto> scaleList = Lists.newArrayList();

        // 计算各类讲座比例
        lectureEntityList.forEach(item -> {
            // 讲座类型名称_由于该值是数据字典，后期可能需要单独取值进行改动
            String venueType = item.getLectureType();
            // 对应讲座类型的数量
            Integer venueTypeNumber = item.getLectureTypeNumber();

            //计算比例
            LectureScaleDto lectureScaleDto = LectureScaleDto.builder()
                    .name(venueType)
                    .scale(MathUtil.calPercent(new BigDecimal(venueTypeNumber), new BigDecimal(total), 2).toString())
                    .build();

            scaleList.add(lectureScaleDto);
        });

        return LecturePicDto.builder()
                .total(total)
                .list(scaleList)
                .build();
    }

    /**
     * 根据场馆id查询讲座信息_分页
     *
     * @param vo
     * @return
     */
    public IPage<LectureDto> queryLecturePageByVenueId(LecturePageByVenueIdVo vo) {
        return baseMapper.selectPage(
                new Page<>(vo.getPageNo(), vo.getPageSize()),
                new LambdaQueryWrapper<LectureEntity>()
                        .eq(LectureEntity::getStatus, 1)
                        .eq(LectureEntity::getVenueId, vo.getVenueId())
        ).convert(v -> JSONObject.parseObject(JSONObject.toJSONString(v), LectureDto.class));
    }
}