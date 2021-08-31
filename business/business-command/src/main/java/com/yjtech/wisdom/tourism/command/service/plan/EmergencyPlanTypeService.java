package com.yjtech.wisdom.tourism.command.service.plan;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yjtech.wisdom.tourism.command.dto.plan.EmergencyPlanTypeUpdateDto;
import com.yjtech.wisdom.tourism.command.entity.plan.EmergencyPlanEntity;
import com.yjtech.wisdom.tourism.command.entity.plan.EmergencyPlanTypeEntity;
import com.yjtech.wisdom.tourism.command.mapper.plan.EmergencyPlanMapper;
import com.yjtech.wisdom.tourism.command.mapper.plan.EmergencyPlanTypeMapper;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.ValidList;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.yjtech.wisdom.tourism.common.utils.AssertUtil.DEFAULT_ERROR_CODE;

/**
 * <p>
 * 应急预案-类型管理 服务类
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-19
 */
@Service
public class EmergencyPlanTypeService extends ServiceImpl<EmergencyPlanTypeMapper, EmergencyPlanTypeEntity>{


    @Autowired
    private EmergencyPlanMapper emergencyPlanMapper;

    @Transactional(rollbackFor = Exception.class)
    public JsonResult create(ValidList<EmergencyPlanTypeUpdateDto> dtos) {
        AssertUtil.isFalse(CollectionUtils.isEmpty(dtos), "不能为空");
        //通过输入值 与数据库中的值做对比  判断删除 更新 新增
        List<EmergencyPlanTypeEntity> list = BeanMapper.mapList(dtos, EmergencyPlanTypeEntity.class);
        if (CollectionUtils.isEmpty(list)) {
            return JsonResult.ok();
        }


        //  存在id需要与数据库做对比判断更新还是删除
        Set<Long> requestSet = list.stream().filter(entity -> Objects.nonNull(entity.getId())).map(entity -> entity.getId()).collect(Collectors.toSet());

        List<EmergencyPlanTypeEntity> dataBase = this.list();
        Set<Long> dataBaseSet = Optional.ofNullable(dataBase).orElse(Lists.newArrayList()).stream()
                .map(entity -> entity.getId()).collect(Collectors.toSet());

        //数据库中存在  请求不存在  将这部分删除
        Set<Long> difference = Sets.difference(dataBaseSet, requestSet);
        for (Long id : difference) {
            //检查是否被引用
            int count = emergencyPlanMapper.selectCount(new LambdaQueryWrapper<EmergencyPlanEntity>().eq(EmergencyPlanEntity::getType, id));
            if (count >= 1) {
                EmergencyPlanTypeEntity emergencyPlanTypeEntity = this.getById(id);
                throw new CustomException(DEFAULT_ERROR_CODE, "\"" + emergencyPlanTypeEntity.getName() + "\"已有记录关联，不能被删除");
            }
            LambdaUpdateWrapper<EmergencyPlanTypeEntity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(EmergencyPlanTypeEntity::getDeleted, EntityConstants.DELETED);
            updateWrapper.eq(EmergencyPlanTypeEntity::getId, id);
            this.update(new EmergencyPlanTypeEntity(), updateWrapper);
        }

        // 交集部分 更新
        Set<Long> intersection = Sets.intersection(dataBaseSet, requestSet);
        List<EmergencyPlanTypeEntity> updateList = list.stream().filter(entity -> intersection.contains(entity.getId())).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(updateList)){
            this.updateBatchById(updateList);
        }


        //  不存在id的是新增
        List<EmergencyPlanTypeEntity> saveList = list.stream().filter(entity -> Objects.isNull(entity.getId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(saveList)) {
            this.saveBatch(saveList);
        }
        return JsonResult.ok();
    }
}
