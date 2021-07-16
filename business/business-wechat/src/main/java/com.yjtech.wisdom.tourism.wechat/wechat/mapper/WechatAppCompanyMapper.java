package com.yjtech.wisdom.tourism.wechat.wechat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.wechat.wechat.entity.WechatAppCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: wuyongchong
 * @date: 2020/8/27 11:09
 */
public interface WechatAppCompanyMapper extends BaseMapper<WechatAppCompany> {

    void unbindScenicApp(@Param("companyIdList") List<Long> companyIdList,@Param("appId") String appId);
}
