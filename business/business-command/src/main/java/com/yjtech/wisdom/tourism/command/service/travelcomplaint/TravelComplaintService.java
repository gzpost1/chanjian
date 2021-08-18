package com.yjtech.wisdom.tourism.command.service.travelcomplaint;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintDTO;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintListDTO;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintStatusStatisticsDTO;
import com.yjtech.wisdom.tourism.command.entity.travelcomplaint.TravelComplaintEntity;
import com.yjtech.wisdom.tourism.command.mapper.travelcomplaint.TravelComplaintMapper;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.*;
import com.yjtech.wisdom.tourism.common.bean.*;
import com.yjtech.wisdom.tourism.common.constant.CacheKeyContants;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.StatusParam;
import com.yjtech.wisdom.tourism.common.enums.TravelComplaintStatusEnum;
import com.yjtech.wisdom.tourism.common.enums.TravelComplaintTypeEnum;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanUtils;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.mybatis.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import com.yjtech.wisdom.tourism.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

/**
 * 旅游投诉(TbTravelComplaint)表服务实现类
 *
 * @author horadirm
 * @since 2021-07-21 09:27:09
 */
@Slf4j
@Service
public class TravelComplaintService extends ServiceImpl<TravelComplaintMapper, TravelComplaintEntity> {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SysUserService sysUserService;


    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int create(TravelComplaintCreateVO vo) {
        TravelComplaintEntity entity = new TravelComplaintEntity();
        entity.build(vo);

        return baseMapper.insert(entity);
    }

    /**
     * 编辑
     *
     * @param vo
     * @return
     */
    public int modify(TravelComplaintUpdateVO vo) {
        //获取旅游投诉信息
        TravelComplaintEntity complaintEntity = baseMapper.selectById(vo.getId());
        Assert.notNull(complaintEntity, "更新状态失败：旅游投诉信息不存在");

        Assert.isTrue(TravelComplaintStatusEnum.TRAVEL_COMPLAINT_STATUS_NO_ASSIGN.getValue().equals(complaintEntity.getStatus()), "更新状态失败：旅游投诉信息状态异常");

        complaintEntity.build(vo);

        return baseMapper.updateById(complaintEntity);
    }

    /**
     * 根据id查询实体
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public TravelComplaintEntity queryEntityById(Long id) {
        return baseMapper.queryEntityById(id);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long id) {
        return baseMapper.deleteById(id);
    }

    /**
     * 根据id查询信息
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public TravelComplaintDTO queryById(Long id) {
        TravelComplaintEntity complaintEntity = baseMapper.queryEntityById(id);

        TravelComplaintDTO dto = new TravelComplaintDTO();
        BeanUtils.copyProperties(complaintEntity, dto);
        dto.buildDesc();

        //获取指定处理人名称
        List<String> userNameList = sysUserService.selectUserNameListById(dto.getAssignAcceptUserId());
        dto.setAssignAcceptUser(userNameList);

        return dto;
    }

    /**
     * 查询分页
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public IPage<TravelComplaintListDTO> queryForPage(TravelComplaintQueryVO vo) {
        return baseMapper.queryForPage(new Page(vo.getPageNo(), vo.getPageSize()), vo);
    }

    /**
     * 更新状态
     *
     * @param statusParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(StatusParam statusParam) {
        //获取旅游投诉信息
        TravelComplaintEntity complaintEntity = baseMapper.selectById(statusParam.getId());
        Assert.notNull(complaintEntity, "更新状态失败：旅游投诉信息不存在");

        complaintEntity.build(statusParam);

        return baseMapper.updateById(complaintEntity);
    }

    /**
     * 处理旅游投诉
     *
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int dealTravelComplaint(TravelComplaintDealVO vo, SysUser sysUser) {
        //获取旅游投诉信息
        TravelComplaintEntity complaintEntity = baseMapper.queryEntityById(vo.getId());
        Assert.notNull(complaintEntity, "处理投诉失败：旅游投诉信息不存在");

        //校验当前用户是否为指定处理人
        Assert.isTrue(null != complaintEntity.getAssignAcceptUserId() && !complaintEntity.getAssignAcceptUserId().isEmpty(), "处理旅游投诉失败：未指定处理人");
        Assert.isTrue(complaintEntity.getAssignAcceptUserId().contains(sysUser.getUserId().toString()), "处理旅游投诉失败：当前用户无权限");

        LambdaUpdateWrapper<TravelComplaintEntity> updateWrapper = new UpdateWrapper<TravelComplaintEntity>().lambda()
                .set(TravelComplaintEntity::getAcceptUserId, sysUser.getUserId())
                .set(TravelComplaintEntity::getAcceptOrganization, vo.getAcceptOrganization())
                .set(TravelComplaintEntity::getAcceptTime, vo.getAcceptTime())
                .set(TravelComplaintEntity::getAcceptResult, vo.getAcceptResult())
                //默认已处理
                .set(TravelComplaintEntity::getStatus, TravelComplaintStatusEnum.TRAVEL_COMPLAINT_STATUS_DEAL_FINISHED.getValue())
                ;

        int result = baseMapper.update(complaintEntity, updateWrapper);

        if (result > 0) {
            //todo：进行消息推送
        }

        return result;
    }

    /**
     * 查询状态统计
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public TravelComplaintStatusStatisticsDTO queryStatusStatistics(TravelComplaintQueryVO vo) {
        return baseMapper.queryStatusStatistics(vo);
    }

    /**
     * 配置/刷新指派人员
     * @param assignUserInfo
     */
    public void refreshAssignUser(AssignUserInfo assignUserInfo){
        //删除已配置的指派人员信息
        redisCache.deleteObject(CacheKeyContants.KEY_ASSIGN_TRAVEL_COMPLAINT);

        redisCache.setCacheObject(CacheKeyContants.KEY_ASSIGN_TRAVEL_COMPLAINT, assignUserInfo);
    }

    /**
     * 配置/刷新处理人员
     * @param dealUserInfo
     */
    public void refreshDealUser(DealUserInfo dealUserInfo){
        //删除已配置的指派人员信息
        redisCache.deleteObject(CacheKeyContants.KEY_DEAL_TRAVEL_COMPLAINT);

        redisCache.setCacheObject(CacheKeyContants.KEY_DEAL_TRAVEL_COMPLAINT, dealUserInfo);

        //更新数据状态为待处理，并且更新该条投诉的指定处理人
        updateStatus(new StatusParam().toBuilder()
                .id(dealUserInfo.getDataId())
                .status(TravelComplaintStatusEnum.TRAVEL_COMPLAINT_STATUS_NO_DEAL.getValue())
                .assignAcceptUserId(dealUserInfo.getAssignUserIdList())
                .build());
    }

    /**
     * 校验投诉类型
     * @param complaintType
     * @param complaintObject
     * @param objectId
     */
    public void checkType(Byte complaintType, String complaintObject, Long objectId){
        if(null != complaintType){
            //当投诉类型为其他时，投诉对象不能为空；当投诉类型为非其他时，投诉对象id不能为空
            Assert.isTrue((TravelComplaintTypeEnum.TRAVEL_COMPLAINT_TYPE_ELSE.getValue().equals(complaintType) && StringUtils.isNotBlank(complaintObject))
                            || (!TravelComplaintTypeEnum.TRAVEL_COMPLAINT_TYPE_ELSE.getValue().equals(complaintType) && Objects.nonNull(objectId)),
                    "新增旅游投诉失败：投诉对象或投诉对象id不能空");
        }
    }



    /** ******************** 大屏 ******************** */

    /**
     * 查询旅游投诉总量
     * @param vo
     * @return
     */
    public Integer queryTravelComplaintTotal(TravelComplaintScreenQueryVO vo){
        LambdaQueryWrapper<TravelComplaintEntity> queryWrapper = new QueryWrapper<TravelComplaintEntity>().lambda()
                .eq(TravelComplaintEntity::getEquipStatus, Objects.isNull(vo.getEquipStatus()) ? EntityConstants.ENABLED : vo.getEquipStatus())
                .between(Objects.nonNull(vo.getBeginTime()) && Objects.nonNull(vo.getEndTime()), TravelComplaintEntity::getComplaintTime, vo.getBeginTime(), vo.getEndTime())
                ;

        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 查询旅游投诉类型分布
     * @param vo
     * @return
     */
    public List<BasePercentVO> queryComplaintTypeDistribution(TravelComplaintScreenQueryVO vo){
        return baseMapper.queryComplaintTypeDistribution(vo);
    }

    /**
     * 查询旅游投诉状态分布
     * @param vo
     * @return
     */
    public List<BasePercentVO> queryComplaintStatusDistribution(TravelComplaintScreenQueryVO vo){
        return baseMapper.queryComplaintStatusDistribution(vo);
    }

    /**
     * 查询旅游投诉类型Top排行
     * @param vo
     * @return
     */
    public List<BaseVO> queryComplaintTopByType(TravelComplaintScreenQueryVO vo){
        return baseMapper.queryComplaintTopByType(vo);
    }

    /**
     * 查询旅游投诉趋势、同比、环比
     * @return
     */
    @Transactional(readOnly = true)
    public List<AnalysisBaseInfo> queryComplaintAnalysis(TravelComplaintScreenQueryVO vo){
        //初始化当年月份信息
        List<String> monthMarkList = DateUtils.getEveryMonthOfCurrentYear();

        //获取当前年度月趋势信息
        List<AnalysisMonthChartInfo> currentAnalysisMonthInfo = this.baseMapper.queryComplaintCurrentAnalysisMonthInfo(vo);
        //获取去年度月趋势信息
        List<AnalysisMonthChartInfo> lastAnalysisMonthInfo = this.baseMapper.queryComplaintLastAnalysisMonthInfo(vo);

        return AnalysisUtils.buildAnalysisInfo(monthMarkList, currentAnalysisMonthInfo, lastAnalysisMonthInfo);
    }



}
