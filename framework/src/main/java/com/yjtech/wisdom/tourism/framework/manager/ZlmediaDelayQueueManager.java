package com.yjtech.wisdom.tourism.framework.manager;

import com.yjtech.wisdom.tourism.common.bean.zlmedia.ZlmediaTaskBaseInfo;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.ZlmediaConstants;
import com.yjtech.wisdom.tourism.common.task.ZlmediaDelayedTask;
import com.yjtech.wisdom.tourism.common.utils.http.HttpUtils;
import com.yjtech.wisdom.tourism.framework.config.ThreadPoolConfig;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.concurrent.DelayQueue;

/**
 * zlmedia延时队列管理
 *
 * @date 2021/9/18 15:58
 * @author horadirm
 */
@Slf4j
@Component
public class ZlmediaDelayQueueManager implements CommandLineRunner {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ThreadPoolConfig threadPoolConfig;

    private DelayQueue<ZlmediaDelayedTask> delayQueue = new DelayQueue<>();

    /**
     * 加入到延时队列中
     * @param task
     */
    public void put(ZlmediaDelayedTask task) {
        log.info("加入延时任务：{}", task);
        delayQueue.put(task);
    }

    /**
     * 取消延时任务
     * @param task
     * @return
     */
    public boolean remove(ZlmediaDelayedTask task) {
        log.info("取消延时任务：{}", task);
        return delayQueue.remove(task);
    }

    /**
     * 取消延时任务
     * @param taskid
     * @return
     */
    public boolean remove(String taskid) {
        return remove(new ZlmediaDelayedTask(new ZlmediaTaskBaseInfo(taskid), 0));
    }

    @Override
    public void run(String... args) {
        log.info("初始化延时队列");
        threadPoolConfig.threadPoolTaskExecutor().execute(this::executeThread);
    }

    /**
     * 延时任务执行线程
     */
    private void executeThread() {
        while (true) {
            try {
                ZlmediaDelayedTask task = delayQueue.take();
                processTask(task);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * 内部执行延时任务
     * @param task
     */
    private void processTask(ZlmediaDelayedTask task) {
        log.info("执行延时任务：{}", task);

        try {
            //构建url
            String url = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY + ZlmediaConstants.ZLMEDIA_SERVER_URL_KEY) + ZlmediaConstants.ZLMEDIA_INTERFACE_ADD_STREAM_PROXY;
            //构建params
            String params = "secret=" + redisCache.getCacheObject(Constants.SYS_CONFIG_KEY + ZlmediaConstants.ZLMEDIA_SECRET_KEY) + "&" +
                    "vhost=" + redisCache.getCacheObject(Constants.SYS_CONFIG_KEY + ZlmediaConstants.ZLMEDIA_HOST_KEY) + "&" +
                    "stream=" + task.getData().getType() + "&" +
                    "app=" + redisCache.getCacheObject(Constants.SYS_CONFIG_KEY + ZlmediaConstants.ZLMEDIA_APP_MODEL_KEY) + "&" +
                    "url=" + URLEncoder.encode(task.getData().getStreamUrl(), Constants.UTF8);

            log.info("ZLMedia新增流代理url：{}", url);

            String result = HttpUtils.sendGet(url, params);

            log.info("ZLMedia新增流代理返回：{}", result);
        }catch (Exception e){
            log.info("ZLMedia新增流代理url构建异常");
            e.printStackTrace();
        }
    }

}
