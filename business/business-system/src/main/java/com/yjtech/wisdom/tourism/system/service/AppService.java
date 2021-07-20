package com.yjtech.wisdom.tourism.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.system.domain.App;
import com.yjtech.wisdom.tourism.system.mapper.AppMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppService extends ServiceImpl<AppMapper, App> {

    public App getAppById(Long id) {
        return Optional.ofNullable(this.getById(id)).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public boolean versionExists(String version, Long id) {
        if (null == id) {
            int count = this
                    .count(new QueryWrapper<App>().and(qw -> {
                        qw.eq("version", version);
                        return qw;
                    }));
            return count >= 1;
        } else {
            int count = this.count(new QueryWrapper<App>().and(qw -> {
                qw.eq("version", version);
                return qw;
            }).ne("id", id));
            return count >= 1;
        }
    }
}
