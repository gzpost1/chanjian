package com.yjtech.wisdom.tourism.resource.turnstile.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.resource.turnstile.entity.Turnstile;
import com.yjtech.wisdom.tourism.resource.turnstile.mapper.TurnstileMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TurnstileService extends ServiceImpl<TurnstileMapper, Turnstile> {

    public Turnstile checkAndGet(Long id) {
        return Optional.ofNullable(this.getById(id)).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public boolean snExists(String sn, Long iconId) {
        int count;
        if (null == iconId) {
            count = this
                    .count(new QueryWrapper<Turnstile>().and(qw -> {
                        qw.eq("sn", sn);
                        return qw;
                    }));
        } else {
            count = this.count(new QueryWrapper<Turnstile>().and(qw -> {
                qw.eq("sn", sn);
                return qw;
            }).ne("id", iconId));
        }
        return count >= 1;
    }
}
