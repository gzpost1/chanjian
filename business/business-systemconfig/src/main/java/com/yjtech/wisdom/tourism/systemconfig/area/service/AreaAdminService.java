package com.yjtech.wisdom.tourism.systemconfig.area.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.systemconfig.area.dto.AreaAdminDto;
import com.yjtech.wisdom.tourism.systemconfig.area.dto.AreaAdminUpdateDto;
import com.yjtech.wisdom.tourism.systemconfig.area.entity.AreaAdminEntity;
import com.yjtech.wisdom.tourism.systemconfig.area.mapper.AreaAdminMapper;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 行政区域信息表 服务类
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-02
 */
@Service
public class AreaAdminService extends ServiceImpl<AreaAdminMapper, AreaAdminEntity>{


    public AreaAdminEntity checkData(AreaAdminDto dto){
        String provinceCode = dto.getProvinceCode();
        String cityCode = dto.getCityCode();
        String code;
        if (StringUtils.isBlank(cityCode)) {
            LambdaQueryWrapper<AreaAdminEntity> queryWrapperProvince = new LambdaQueryWrapper<AreaAdminEntity>()
                    .eq(AreaAdminEntity::getCode, provinceCode);
            AssertUtil.isFalse(0 < this.count(queryWrapperProvince), "该省份已存在");
            code = provinceCode;
        } else {
            LambdaQueryWrapper<AreaAdminEntity> queryWrapperProvince = new LambdaQueryWrapper<AreaAdminEntity>().eq(AreaAdminEntity::getCode, cityCode);
            AssertUtil.isFalse(0 < this.count(queryWrapperProvince), "该城市已存在");
            AssertUtil.isFalse(StringUtils.isBlank(dto.getPlate()), "车牌首字母不能为空");
            code = cityCode;
        }
        AreaAdminEntity entity = BeanMapper.map(dto, AreaAdminEntity.class);
        entity.setCode(code);
        return entity;
    }

    public void update(AreaAdminUpdateDto updateDto){
        String cityCode = updateDto.getCityCode();
        String code;
        AreaAdminEntity adminEntity = this.getById(updateDto.getCode());
        AssertUtil.isFalse(Objects.isNull(adminEntity), "该记录不存在");

        LambdaQueryWrapper<AreaAdminEntity> queryWrapperAdministration = new LambdaQueryWrapper<AreaAdminEntity>()
                .eq(AreaAdminEntity::getAdministration, updateDto.getAdministration());
        if(!Objects.equals(adminEntity.getAdministration(),updateDto.getAdministration())){
            AssertUtil.isFalse(0 < this.count(queryWrapperAdministration), "该行政区已存在");
        }

        if (StringUtils.isBlank(cityCode)) {
            code = updateDto.getProvinceCode();
        } else {
            AssertUtil.isFalse(StringUtils.isBlank(updateDto.getPlate()), "车牌首字母不能为空");
            code = cityCode;
        }
        AreaAdminEntity entity = BeanMapper.map(updateDto, AreaAdminEntity.class);
        entity.setCode(code);
        LambdaUpdateWrapper<AreaAdminEntity> updateWrapper = new LambdaUpdateWrapper<>();
        //将areacode作为主键  修改时主键也要变化
        updateWrapper.set(AreaAdminEntity::getCode,entity.getCode());
        updateWrapper.eq(AreaAdminEntity::getCode,updateDto.getCode());
        this.update(entity,updateWrapper);
    }
}
