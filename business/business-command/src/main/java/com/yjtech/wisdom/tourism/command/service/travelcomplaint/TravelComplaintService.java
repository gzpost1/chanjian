package com.yjtech.wisdom.tourism.command.service.travelcomplaint;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintDTO;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintListDTO;
import com.yjtech.wisdom.tourism.command.entity.travelcomplaint.TravelComplaintEntity;
import com.yjtech.wisdom.tourism.command.mapper.travelcomplaint.TravelComplaintMapper;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintCreateVO;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintDealVO;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintQueryVO;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintUpdateVO;
import com.yjtech.wisdom.tourism.common.bean.AssignUserInfo;
import com.yjtech.wisdom.tourism.common.bean.DealUserInfo;
import com.yjtech.wisdom.tourism.common.constant.CacheKeyContants;
import com.yjtech.wisdom.tourism.common.core.domain.StatusParam;
import com.yjtech.wisdom.tourism.common.enums.TravelComplaintStatusEnum;
import com.yjtech.wisdom.tourism.common.enums.TravelComplaintTypeEnum;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanUtils;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
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

        LambdaUpdateWrapper<TravelComplaintEntity> updateWrapper = new UpdateWrapper<TravelComplaintEntity>().lambda()
                .set(Objects.nonNull(vo.getComplaintType()), TravelComplaintEntity::getComplaintType, vo.getComplaintType())
                .set(StringUtils.isNotBlank(vo.getComplaintObject()), TravelComplaintEntity::getComplaintObject, vo.getComplaintObject())
                .set(Objects.nonNull(vo.getObjectId()), TravelComplaintEntity::getObjectId, vo.getObjectId())
                .set(StringUtils.isNotBlank(vo.getLocation()), TravelComplaintEntity::getLocation, vo.getLocation())
                .set(Objects.nonNull(vo.getLongitude()), TravelComplaintEntity::getLongitude, vo.getLongitude())
                .set(Objects.nonNull(vo.getLatitude()), TravelComplaintEntity::getLatitude, vo.getLatitude())
                .set(StringUtils.isNotBlank(vo.getContactUser()), TravelComplaintEntity::getContactUser, vo.getContactUser())
                .set(Objects.nonNull(vo.getContactMobile()) && !vo.getContactMobile().isEmpty(), TravelComplaintEntity::getContactMobile, vo.getContactMobile())
                .set(StringUtils.isNotBlank(vo.getComplaintReason()), TravelComplaintEntity::getComplaintReason, vo.getComplaintReason())
                .set(Objects.nonNull(vo.getImage()) && !vo.getImage().isEmpty(), TravelComplaintEntity::getImage, vo.getImage());

        return baseMapper.update(complaintEntity, updateWrapper);
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

        //todo:获取投诉对象名称（景区、酒店）

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

        LambdaUpdateWrapper<TravelComplaintEntity> updateWrapper = new UpdateWrapper<TravelComplaintEntity>().lambda()
                .set(Objects.nonNull(statusParam.getStatus()), TravelComplaintEntity::getStatus, statusParam.getStatus())
                .set(Objects.nonNull(statusParam.getEquipStatus()), TravelComplaintEntity::getEquipStatus, statusParam.getEquipStatus());

        return baseMapper.update(complaintEntity, updateWrapper);
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
        TravelComplaintEntity complaintEntity = baseMapper.selectById(vo.getId());
        Assert.notNull(complaintEntity, "处理投诉失败：旅游投诉信息不存在");

        //校验当前用户是否为指定处理人
        Assert.isTrue(null != complaintEntity.getAssignAcceptUserId() && !complaintEntity.getAssignAcceptUserId().isEmpty(), "处理旅游投诉失败：未指定处理人");
        Assert.isTrue(complaintEntity.getAssignAcceptUserId().contains(sysUser.getUserId().toString()), "处理旅游投诉失败：当前用户无权限");

        LambdaUpdateWrapper<TravelComplaintEntity> updateWrapper = new UpdateWrapper<TravelComplaintEntity>().lambda()
                .set(TravelComplaintEntity::getAcceptUserId, vo.getAcceptUserId())
                .set(TravelComplaintEntity::getAcceptOrganization, vo.getAcceptOrganization())
                .set(TravelComplaintEntity::getAcceptTime, vo.getAcceptTime())
                .set(TravelComplaintEntity::getAcceptResult, vo.getAcceptResult());

        int result = baseMapper.update(complaintEntity, updateWrapper);

        if (result > 0) {
            //todo：进行消息推送
        }

        return result;
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

}
