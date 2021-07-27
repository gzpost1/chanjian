package com.yjtech.wisdom.tourism.integration.service;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.path.DistrictPathEnum;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.common.utils.JsonUtils;
import com.yjtech.wisdom.tourism.integration.pojo.bo.districtbigdata.DistrictLoginBO;
import com.yjtech.wisdom.tourism.system.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 区县大数据请求方法
 *
 * @author renguangqian
 * @date 2021/7/27 17:03
 */
@Service
@Slf4j
public class DistrictBigDataService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SysConfigService sysConfigService;

    @Value("${tourist.districtHost}")
    private String districtHost;

    @Value("${tourist.configAccountKey}")
    private String configAccountKey;

    @Value("${tourist.configPasswordKey}")
    private String configPasswordKey;

    /**
     * 区县大数据请求方法
     *
     * @param vo
     * @param <Vo>
     * @return
     */
    public  <Vo> String requestDistrict (String path, Vo vo, String desc) {

        String url = districtHost + path;

        // 获取参数设置中的区县大数据 账号、密码
        String account = sysConfigService.selectConfigByKey(configAccountKey);
        String password = sysConfigService.selectConfigByKey(configPasswordKey);

        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
            throw new ClassCastException("【区县大数据】账号：" + account + "\n" + "【区县大数据】密码：" + password + "\n" + "账号/密码不能为空！请在参数配置模块，进行配置！");
        }

        // token生成规则
        String tokenKey = account + "_" + DateUtils.dateTime();
        String token = getToken(account, password, tokenKey);

        log.info("【{}】-请求URL：{}", desc, url);
        log.info("【{}】-请求入参：{}", desc, JSONObject.toJSONString(vo));
        String result = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body(JSONObject.toJSONString(vo))
                .execute()
                .body();
        log.info("【{}】-请求返回：{}", desc, result);

        return result;
    }

    /**
     * 区县大数据获取token
     *
     * @param tokenKey
     * @param account
     * @param password
     * @return
     */
    private String getToken(String account, String password, String tokenKey) {
        // redis获取 token
        String token = redisTemplate.opsForValue().get(tokenKey);

        // token为空 则重新登录获取
        if (StringUtils.isEmpty(token)) {
            log.info("【区县大数据】token失效，重新登录！");
            // 进行登录获取有效token
            String loginUlr = districtHost + DistrictPathEnum.LOGIN.getPath();

            log.info("【区县大数据】-登录接口URL：{}", loginUlr);
            log.info("【区县大数据】-用户名：{}", account);
            log.info("【区县大数据】-密码：{}", password);

            // 构建登录请求参数
            DistrictLoginBO districtLoginDto = DistrictLoginBO.builder()
                    .account(account)
                    .password(password)
                    .loginType(3)
                    .build();

            String result = HttpRequest.post(loginUlr)
                    .header("Content-Type", "application/json")
                    .body(JSONObject.toJSONString(districtLoginDto))
                    .execute()
                    .body();
            log.info("【区县大数据】-返回结果：{}", result);
            String authorization = "Bearer " + JsonUtils.getValueByKey(result, "authorization");
            log.info("【区县大数据】-Authorization：{}", authorization);
            // 8小时过期
            redisTemplate.opsForValue().set(tokenKey, authorization, 7, TimeUnit.HOURS);
        }
        log.info("【区县大数据】token：{}", token);
        return token;
    }
}
