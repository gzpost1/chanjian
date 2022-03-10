package com.yjtech.wisdom.tourism.bigscreen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.mapper.TbRegisterInfoMapper;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 注册信息 服务实现类
 *
 * @author Mujun
 * @since 2022-03-02
 */
@Service
public class TbRegisterInfoService extends BaseMybatisServiceImpl<TbRegisterInfoMapper, TbRegisterInfoEntity>  {

 public TbRegisterInfoEntity queryByPhone(String phone){
     QueryWrapper<TbRegisterInfoEntity> queryWrapper  = new QueryWrapper<>();
     queryWrapper.eq(TbRegisterInfoEntity.PHONE,phone);
     TbRegisterInfoEntity tbRegisterInfoEntity = baseMapper.selectOne(queryWrapper);
     return tbRegisterInfoEntity;
 }
 public boolean checkPhone(String phone){
     QueryWrapper<TbRegisterInfoEntity> queryWrapper  = new QueryWrapper<>();
     queryWrapper.eq(TbRegisterInfoEntity.PHONE,phone);
     List<TbRegisterInfoEntity> list = baseMapper.selectList(queryWrapper);
     if(list.size()==0){
         return true;
     }
     return false;
 }

}
