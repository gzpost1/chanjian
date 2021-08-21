package com.yjtech.wisdom.tourism.command.service.travelcomplaint;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintDTO;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintListDTO;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintStatusStatisticsDTO;
import com.yjtech.wisdom.tourism.command.entity.travelcomplaint.TravelComplaintEntity;
import com.yjtech.wisdom.tourism.command.mapper.travelcomplaint.TravelComplaintMapper;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.*;
import com.yjtech.wisdom.tourism.common.bean.*;
import com.yjtech.wisdom.tourism.common.constant.CacheKeyContants;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.constant.TemplateConstants;
import com.yjtech.wisdom.tourism.common.core.domain.StatusParam;
import com.yjtech.wisdom.tourism.common.enums.MessageEventTypeEnum;
import com.yjtech.wisdom.tourism.common.enums.MessagePlatformTypeEnum;
import com.yjtech.wisdom.tourism.common.enums.TravelComplaintStatusEnum;
import com.yjtech.wisdom.tourism.common.enums.TravelComplaintTypeEnum;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.sms.MessageCall;
import com.yjtech.wisdom.tourism.common.sms.MessageCallDto;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanUtils;
import com.yjtech.wisdom.tourism.hotel.service.TbHotelInfoService;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.message.admin.service.MessageMangerService;
import com.yjtech.wisdom.tourism.message.admin.vo.SendMessageVo;
import com.yjtech.wisdom.tourism.mybatis.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import com.yjtech.wisdom.tourism.resource.scenic.service.ScenicService;
import com.yjtech.wisdom.tourism.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
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
public class TravelComplaintService extends ServiceImpl<TravelComplaintMapper, TravelComplaintEntity> implements MessageCall {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MessageMangerService messageMangerService;
    @Autowired
    private TbHotelInfoService tbHotelInfoService;
    @Autowired
    private ScenicService scenicService;


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

        int result = baseMapper.insert(entity);

        if (result > 0) {
            //构建后台消息模板
            String platformTemplate = MessageFormat.format(
                    TemplateConstants.TEMPLATE_PLATFORM_TRAVEL_COMPLAINT_INSERT,
                    TravelComplaintTypeEnum.getDescByValue(vo.getComplaintType()),
                    getComplaintObject(entity.getComplaintType(), entity.getComplaintObject(), entity.getObjectId()),
                    vo.getComplaintTime().toString(),
                    vo.getLocation());
            //发送消息
            sendMessageNotice(CacheKeyContants.KEY_ASSIGN_TRAVEL_COMPLAINT,
                    entity.getId(),
                    null,
                    null,
                    platformTemplate,
                    null,
                    AssignUserInfo.class);
        }

        return result;
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

        int result = baseMapper.updateById(complaintEntity);

        //更新成功，且数据状态为待处理时，发送消息
        if(result > 0 && TravelComplaintStatusEnum.TRAVEL_COMPLAINT_STATUS_NO_DEAL.getValue().equals(complaintEntity.getStatus())){
            //获取投诉对象名称
            String complaintObject = getComplaintObject(complaintEntity.getComplaintType(), complaintEntity.getComplaintObject(), complaintEntity.getObjectId());
            //构建后台消息模板
            String platformTemplate = MessageFormat.format(
                    TemplateConstants.TEMPLATE_PLATFORM_TRAVEL_COMPLAINT_ASSIGN,
                    TravelComplaintTypeEnum.getDescByValue(complaintEntity.getComplaintType()),
                    complaintObject,
                    complaintEntity.getComplaintTime().toString(),
                    complaintEntity.getLocation());
            //构建App消息模板
            String appTemplate = MessageFormat.format(
                    TemplateConstants.TEMPLATE_APP_TRAVEL_COMPLAINT_ASSIGN,
                    complaintObject);
            //发送消息
            sendMessageNotice(CacheKeyContants.KEY_ASSIGN_TRAVEL_COMPLAINT,
                    complaintEntity.getId(),
                    appTemplate,
                    appTemplate,
                    platformTemplate,
                    Arrays.asList(complaintObject),
                    AssignUserInfo.class);
        }

        return result;
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
                .set(TravelComplaintEntity::getStatus, TravelComplaintStatusEnum.TRAVEL_COMPLAINT_STATUS_DEAL_FINISHED.getValue());

        int result = baseMapper.update(complaintEntity, updateWrapper);

        if (result > 0) {
            //todo：是否进行消息推送
        }

        return result;
    }

    /**
     * 查询状态统计
     *
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public TravelComplaintStatusStatisticsDTO queryStatusStatistics(TravelComplaintQueryVO vo) {
        return baseMapper.queryStatusStatistics(vo);
    }

    /**
     * 配置/刷新指派人员
     *
     * @param assignUserInfo
     */
    public void refreshAssignUser(AssignUserInfo assignUserInfo) {
        //删除已配置的指派人员信息
        redisCache.deleteObject(CacheKeyContants.KEY_ASSIGN_TRAVEL_COMPLAINT);

        redisCache.setCacheObject(CacheKeyContants.KEY_ASSIGN_TRAVEL_COMPLAINT, assignUserInfo);
    }

    /**
     * 配置/刷新处理人员
     *
     * @param dealUserInfo
     */
    public void refreshDealUser(DealUserInfo dealUserInfo) {
        //删除已配置的指派人员信息
        redisCache.deleteObject(CacheKeyContants.KEY_DEAL_TRAVEL_COMPLAINT + dealUserInfo.getDataId());

        redisCache.setCacheObject(CacheKeyContants.KEY_DEAL_TRAVEL_COMPLAINT + dealUserInfo.getDataId(), dealUserInfo);

        //更新数据状态为待处理，并且更新该条投诉的指定处理人
        updateStatus(new StatusParam().toBuilder()
                .id(dealUserInfo.getDataId())
                .status(TravelComplaintStatusEnum.TRAVEL_COMPLAINT_STATUS_NO_DEAL.getValue())
                .assignAcceptUserId(dealUserInfo.getAssignUserIdList())
                .build());
    }

    /**
     * 获取指派人员
     */
    public List<Long> queryAssignUser() {
        //获取当前指派人员信息
        Object cacheObject = redisCache.getCacheObject(CacheKeyContants.KEY_ASSIGN_TRAVEL_COMPLAINT);
        if (null != cacheObject) {
            AssignUserInfo assignUserInfo = JSONObject.parseObject(JSONObject.toJSONString(cacheObject), AssignUserInfo.class);
            //获取指派人员id
            return assignUserInfo.getAssignUserIdList();
        }
        return Collections.emptyList();
    }

    /**
     * 获取处理人员
     *
     * @param dataId
     * @return
     */
    public List<Long> queryDealUser(Long dataId) {
        //获取当前处理人员信息
        Object cacheObject = redisCache.getCacheObject(CacheKeyContants.KEY_DEAL_TRAVEL_COMPLAINT + dataId);

        if (null != cacheObject) {
            DealUserInfo dealUserInfo = JSONObject.parseObject(JSONObject.toJSONString(cacheObject), DealUserInfo.class);
            //获取处理人员id
            return dealUserInfo.getAssignUserIdList();
        }
        return Collections.emptyList();
    }

    /**
     * 校验投诉类型
     *
     * @param complaintType
     * @param complaintObject
     * @param objectId
     */
    public void checkType(Byte complaintType, String complaintObject, Long objectId) {
        if (null != complaintType) {
            //当投诉类型为其他时，投诉对象不能为空；当投诉类型为非其他时，投诉对象id不能为空
            Assert.isTrue((TravelComplaintTypeEnum.TRAVEL_COMPLAINT_TYPE_ELSE.getValue().equals(complaintType) && StringUtils.isNotBlank(complaintObject))
                            || (!TravelComplaintTypeEnum.TRAVEL_COMPLAINT_TYPE_ELSE.getValue().equals(complaintType) && Objects.nonNull(objectId)),
                    "新增旅游投诉失败：投诉对象或投诉对象id不能空");
        }
    }


    /** ******************** 大屏 ******************** */

    /**
     * 查询旅游投诉总量
     *
     * @param vo
     * @return
     */
    public Integer queryTravelComplaintTotal(TravelComplaintScreenQueryVO vo) {
        LambdaQueryWrapper<TravelComplaintEntity> queryWrapper = new QueryWrapper<TravelComplaintEntity>().lambda()
                .eq(TravelComplaintEntity::getEquipStatus, Objects.isNull(vo.getEquipStatus()) ? EntityConstants.ENABLED : vo.getEquipStatus())
                .between(Objects.nonNull(vo.getBeginTime()) && Objects.nonNull(vo.getEndTime()), TravelComplaintEntity::getComplaintTime, vo.getBeginTime(), vo.getEndTime());

        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 查询旅游投诉类型分布
     *
     * @param vo
     * @return
     */
    public List<BasePercentVO> queryComplaintTypeDistribution(TravelComplaintScreenQueryVO vo) {
        return baseMapper.queryComplaintTypeDistribution(vo);
    }

    /**
     * 查询旅游投诉状态分布
     *
     * @param vo
     * @return
     */
    public List<BasePercentVO> queryComplaintStatusDistribution(TravelComplaintScreenQueryVO vo) {
        return baseMapper.queryComplaintStatusDistribution(vo);
    }

    /**
     * 查询旅游投诉类型Top排行
     *
     * @param vo
     * @return
     */
    public List<BaseVO> queryComplaintTopByType(TravelComplaintScreenQueryVO vo) {
        return baseMapper.queryComplaintTopByType(vo);
    }

    /**
     * 查询旅游投诉趋势、同比、环比
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<AnalysisBaseInfo> queryComplaintAnalysis(TravelComplaintScreenQueryVO vo) {
        //初始化当年月份信息
        List<String> monthMarkList = DateUtils.getEveryMonthOfCurrentYear();

        //获取当前年度月趋势信息
        List<AnalysisMonthChartInfo> currentAnalysisMonthInfo = this.baseMapper.queryComplaintCurrentAnalysisMonthInfo(vo);
        //获取去年度月趋势信息
        List<AnalysisMonthChartInfo> lastAnalysisMonthInfo = this.baseMapper.queryComplaintLastAnalysisMonthInfo(vo);

        return AnalysisUtils.buildAnalysisInfo(monthMarkList, currentAnalysisMonthInfo, lastAnalysisMonthInfo);
    }

    /**
     * 获取投诉对象名称
     * @param complaintType
     * @param complaintObject
     * @param objectId
     * @return
     */
    private String getComplaintObject(Byte complaintType, String complaintObject, Long objectId){
        if(TravelComplaintTypeEnum.TRAVEL_COMPLAINT_TYPE_ELSE.getValue().equals(complaintType)){
            return complaintObject;
        }
        return TravelComplaintTypeEnum.TRAVEL_COMPLAINT_TYPE_SCENIC.getValue().equals(complaintType) ? scenicService.queryNameById(objectId) : tbHotelInfoService.queryNameById(objectId);
    }

    /**
     * 发送消息
     * @param cacheKey
     * @param dataId
     * @param title
     * @param content
     * @param pcTitle
     * @param smsContent
     * @param tClass
     * @param <T>
     */
    private <T> void sendMessageNotice(String cacheKey, Long dataId, String title, String content, String pcTitle, List<String> smsContent, Class<T> tClass) {
        //构建用户id列表
        List<Long> eventDealPersonIdArray = Lists.newArrayList();
        //构建发送类型
        List<Integer> sendType = Lists.newArrayList();
        //发送标识
        Boolean sendFlag = false;

        //获取当前指派/处理人员信息
        Object cacheObject = redisCache.getCacheObject(cacheKey);
        if (null == cacheObject) {
            //默认给超管用户发送消息
            eventDealPersonIdArray.add(messageMangerService.queryAdimnId());
            sendFlag = true;
        } else {
            try {
                //获取指派/处理人员信息
                T cacheInfo = JSONObject.parseObject(JSONObject.toJSONString(cacheObject), tClass);
                //获取用户id列表
                Field assignUserIdList = cacheInfo.getClass().getDeclaredField("assignUserIdList");
                if (null != assignUserIdList) {
                    assignUserIdList.setAccessible(true);
                    eventDealPersonIdArray = (List<Long>) assignUserIdList.get(cacheInfo);
                }
                //获取平台通知标识
                Field platformNoticeFlag = cacheInfo.getClass().getDeclaredField("platformNoticeFlag");
                if (null != platformNoticeFlag) {
                    platformNoticeFlag.setAccessible(true);
                    Boolean platformFlag = (Boolean) platformNoticeFlag.get(cacheInfo);
                    if (null != platformFlag && platformFlag) {
                        //设置发送类型
                        sendType.add(MessagePlatformTypeEnum.MESSAGE_PLATFORM_TYPE_BACK.getValue().intValue());
                        sendFlag = true;
                    }
                }
                //获取App消息通知标识
                Field appNoticeFlag = cacheInfo.getClass().getDeclaredField("appNoticeFlag");
                if (null != appNoticeFlag) {
                    appNoticeFlag.setAccessible(true);
                    Boolean appFlag = (Boolean) appNoticeFlag.get(cacheInfo);
                    if (null != appFlag && appFlag) {
                        //设置发送类型
                        sendType.add(MessagePlatformTypeEnum.MESSAGE_PLATFORM_TYPE_APP.getValue().intValue());
                        sendFlag = true;
                    }
                }
                //获取短信通知标识
                Field textMessageNoticeFlag = cacheInfo.getClass().getDeclaredField("textMessageNoticeFlag");
                if (null != textMessageNoticeFlag) {
                    textMessageNoticeFlag.setAccessible(true);
                    Boolean textMessageFlag = (Boolean) textMessageNoticeFlag.get(cacheInfo);
                    if (null != textMessageFlag && textMessageFlag) {
                        //设置发送类型
                        sendType.add(MessagePlatformTypeEnum.MESSAGE_PLATFORM_TYPE_SHORT_MESSAGE.getValue().intValue());
                        sendFlag = true;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new CustomException(ErrorCode.BUSINESS_EXCEPTION, "发送消息失败：配置消息模板异常");
            }
        }

        if(sendFlag){
            //为指派人推送消息
            messageMangerService.sendMessage(new SendMessageVo(sendType.toArray(new Integer[0]), dataId,
                    MessageEventTypeEnum.MESSAGE_EVENT_TYPE_TRAVEL_COMPLAINT.getValue().intValue(),
                    title, content, pcTitle, smsContent,
                    eventDealPersonIdArray.toArray(new Long[0])));
        }
    }

    /**
     * 通过事件id，查询事件信息
     * @param ids
     * @return
     */
    @Override
    public List<MessageCallDto> queryEvent(Long[] ids) {
       return baseMapper.queryEvent(ids);
    }

}
