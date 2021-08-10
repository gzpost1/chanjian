package com.yjtech.wisdom.tourism.common.utils.zc;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.bean.zc.po.ZcResponse;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 中测请求工具
 *
 * @Date 2020/11/20 14:47
 * @Author horadirm
 */
@Slf4j
public class ZcRequestUtils {

    /**
     * url连接符 ?
     */
    private static final String URL_CONNECTOR_QUESTION_MARK = "?";
    /**
     * url连接符 &
     */
    private static final String URL_CONNECTOR_PARAMS = "&";
    /**
     * url连接符 =
     */
    private static final String URL_CONNECTOR_EQUAL = "=";
    /**
     * 默认超时 30000毫秒
     */
    private static final int DEFAULT_TIME_OUT = 30000;

    /**
     * GET请求 表单参数 获取实体
     *
     * @param url
     * @param header
     * @param params
     * @param rClazz
     * @param urlParamsFlag url是否拼接参数
     * @param <H>
     * @param <P>
     * @param <R>
     * @return
     */
    public static <H, P, R> R getToForm(String url, H header, P params, Class<R> rClazz, boolean urlParamsFlag) {
        return getToForm(url, header, params, rClazz, DEFAULT_TIME_OUT, urlParamsFlag);
    }

    /**
     * GET请求 表单参数 获取列表
     *
     * @param url
     * @param header
     * @param params
     * @param rClazz
     * @param urlParamsFlag url是否拼接参数
     * @param <H>
     * @param <P>
     * @param <R>
     * @return
     */
    public static <H, P, R> List<R> getListToForm(String url, H header, P params, Class<R> rClazz, boolean urlParamsFlag) {
        return getListToForm(url, header, params, rClazz, DEFAULT_TIME_OUT, urlParamsFlag);
    }

    /**
     * GET请求 表单参数 获取实体
     *
     * @param url
     * @param header
     * @param params
     * @param rClazz
     * @param timeout
     * @param urlParamsFlag url是否拼接参数
     * @param <H>
     * @param <P>
     * @param <R>
     * @return
     */
    public static <H, P, R> R getToForm(String url, H header, P params, Class<R> rClazz, int timeout, boolean urlParamsFlag) {
        ZcResponse zcResponse = requestGet(url, header, params, timeout, urlParamsFlag);
        return JSONObject.parseObject(zcResponse.getData().toString(), rClazz);
    }

    /**
     * GET请求 表单参数 获取列表
     *
     * @param url
     * @param header
     * @param params
     * @param rClazz
     * @param timeout
     * @param urlParamsFlag url是否拼接参数
     * @param <H>
     * @param <P>
     * @param <R>
     * @return
     */
    public static <H, P, R> List<R> getListToForm(String url, H header, P params, Class<R> rClazz, int timeout, boolean urlParamsFlag) {
        ZcResponse zcResponse = requestGet(url, header, params, timeout, urlParamsFlag);
        return JSONObject.parseArray(zcResponse.getData().toString(), rClazz);
    }

    /**
     * get 请求
     *
     * @param url
     * @param header
     * @param params
     * @param timeout 超时时间，默认30000毫秒
     * @param urlParamsFlag url是否拼接参数
     * @param <H>
     * @param <P>
     * @return
     */
    private static <H, P> ZcResponse requestGet(String url, H header, P params, int timeout, boolean urlParamsFlag) {
        ZcResponse zcResponse;
        try {
            HttpRequest httpRequest = HttpRequest.get(urlParamsFlag ? urlJointParams(url, params) : url)
                    .addHeaders(BeanUtils.beanToMap(header))
                    .form(urlParamsFlag ? null : BeanUtils.beanToMap(params))
                    .timeout(timeout);
            log.info("====================> 中测接口 {} 请求：{} <====================", url, JSONObject.toJSON(httpRequest));

            String responseBody = httpRequest.execute().body();
            log.info("====================> 中测接口 {} 请求返回：{} <====================", url, responseBody);

            zcResponse = JSONObject.parseObject(responseBody, ZcResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.BUSINESS_EXCEPTION, "中测接口请求异常：请求异常，请联系管理员");
        }

        //校验请求结果
        if (zcResponse.isSuccess()) {
            //校验请求数据
            if (zcResponse.isEmpty()) {
                throw new CustomException(ErrorCode.BUSINESS_EXCEPTION, "中测接口请求异常：返回为空");
            }
            return zcResponse;
        }
        throw new CustomException(ErrorCode.BUSINESS_EXCEPTION, "中测接口请求异常：" + zcResponse.getMessage());
    }

    /**
     * 拼接url参数
     *
     * @param url
     * @param params
     * @param <P>
     * @return
     */
    private static <P> String urlJointParams(String url, P params) {
        //获取参数map
        Map<String, Object> paramMap = BeanUtils.beanToMap(params);

        if (paramMap.isEmpty()) {
            return url;
        }

        StringBuilder jointUrl = new StringBuilder(url);
        jointUrl.append(URL_CONNECTOR_QUESTION_MARK);

        //获取参数进行url拼接
        for (String key : paramMap.keySet()) {
            jointUrl.append(key).append(URL_CONNECTOR_EQUAL).append(paramMap.get(key)).append(URL_CONNECTOR_PARAMS);
        }

        return jointUrl.toString();
    }
}
