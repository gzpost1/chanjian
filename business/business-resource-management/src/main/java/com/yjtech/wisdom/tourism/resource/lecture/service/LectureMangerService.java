package com.yjtech.wisdom.tourism.resource.lecture.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.utils.CommonUtil;
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
import com.yjtech.wisdom.tourism.system.domain.IconSpotEnum;
import com.yjtech.wisdom.tourism.system.service.IconService;
import com.yjtech.wisdom.tourism.system.service.SysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 展演讲座管理
 *
 * @author renguangqian
 * @date 2021/7/21 11:25
 */
@Service
public class LectureMangerService extends BaseMybatisServiceImpl<LectureMapper, LectureEntity> {

    @Autowired
    private SysDictDataService sysDictDataService;

    @Autowired
    private IconService iconService;

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
                    .eq(!ObjectUtils.isEmpty(vo.getStatus()), LectureEntity::getStatus, vo.getStatus())
                    .eq(!ObjectUtils.isEmpty(vo.getLectureValue()), LectureEntity::getLectureValue, vo.getLectureValue())
                    .ge(!StringUtils.isEmpty(vo.getHoldStartDate()), LectureEntity::getHoldStartDate, vo.getHoldStartDate())
                    .le(!StringUtils.isEmpty(vo.getHoldEndDate()), LectureEntity::getHoldEndDate, vo.getHoldEndDate())
                    .like(!StringUtils.isEmpty(vo.getLectureName()), LectureEntity::getLectureName, vo.getLectureName())
                    .like(!StringUtils.isEmpty(vo.getVenueName()), LectureEntity::getVenueName, vo.getVenueName())
                    .orderByDesc(LectureEntity::getUpdateTime))
        .convert(item -> {
            LectureDto lectureDto = JSONObject.parseObject(JSONObject.toJSONString(item), LectureDto.class);
            // 字典获取
            String name = sysDictDataService.selectDictLabel(lectureDto.getLectureType(), lectureDto.getLectureValue());
            // 设置点位图标
            lectureDto.setIconUrl(iconService.queryIconUrl(IconSpotEnum.LECTURE,
                    Objects.isNull(item.getStatus()) ? "0" : item.getStatus().toString()));
            lectureDto.setDictName(name);
            return lectureDto;
        });
    }

    /**
     * 查询展演讲座分布比列
     *
     * @param vo
     * @return
     */
    public LecturePicDto queryScale(LectureScaleVo vo) {
        QueryWrapper<LectureEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(id) as lectureTypeNumber, lecture_type, lecture_value");
        queryWrapper.eq("status", 1);
        queryWrapper.between("hold_start_date", vo.getBeginTime(), vo.getEndTime());
        queryWrapper.groupBy("lecture_value");

        // 符合条件的讲座类型 的数量
        List<LectureEntity> lectureEntityList = baseMapper.selectList(queryWrapper);

        // 总数
        int total = lectureEntityList.stream().mapToInt(LectureEntity::getLectureTypeNumber).sum();

        List<LectureScaleDto> scaleList = Lists.newArrayList();

        // 计算各类讲座比例
        lectureEntityList.forEach(item -> {
            // 对应讲座类型的数量
            Integer lectureTypeNumber = item.getLectureTypeNumber();

            // 字典获取
            String name = sysDictDataService.selectDictLabel(item.getLectureType(), item.getLectureValue());

            //计算比例
            LectureScaleDto lectureScaleDto = LectureScaleDto.builder()
                    .name(name)
                    .value(lectureTypeNumber)
                    .scale(MathUtil.calPercent(new BigDecimal(lectureTypeNumber), new BigDecimal(total), 2).toString())
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
        ).convert(v -> {
            LectureDto lectureDto = JSONObject.parseObject(JSONObject.toJSONString(v), LectureDto.class);
            // 字典获取
            String name = sysDictDataService.selectDictLabel(lectureDto.getLectureType(), lectureDto.getLectureValue());
            lectureDto.setDictName(name);
            return lectureDto;
        });
    }
}
