package com.yjtech.wisdom.tourism.common.task;

import com.yjtech.wisdom.tourism.common.bean.zlmedia.ZlmediaTaskBaseInfo;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 媒体延时服务
 *
 * @date 2021/9/18 15:39
 * @author horadirm
 */
public class ZlmediaDelayedTask implements Delayed {

    private ZlmediaTaskBaseInfo data;

    private long expire;


    /**
     * 构造延时任务
     * @param data      业务数据
     * @param expire    任务延时时间（ms）
     */
    public ZlmediaDelayedTask(ZlmediaTaskBaseInfo data, long expire) {
        super();
        this.data = data;
        this.expire = expire + System.currentTimeMillis();
    }

    public ZlmediaTaskBaseInfo getData() {
        return data;
    }

    public long getExpire() {
        return expire;
    }

    public ZlmediaDelayedTask(ZlmediaTaskBaseInfo data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ZlmediaDelayedTask) {
            return this.data.getIdentifier().equals(((ZlmediaDelayedTask) obj).getData().getIdentifier());
        }
        return false;
    }

    @Override
    public long getDelay(@NotNull TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis(), unit);
    }

    @Override
    public int compareTo(@NotNull Delayed o) {
        long delta = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return (int) delta;
    }
}
