package com.yjtech.wisdom.tourism.common.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * JSON解析工具类
 *
 * @author renguangqian
 */
public class JsonUtils {

    /**
     * 获取指定json属性对应得数据
     *
     * @param objJson
     * @param reg
     * @return
     */
    public static Object getValueByKey(Object objJson, String reg) {
        if (objJson instanceof String) {
            if (StringUtils.isEmpty(objJson)) {
                return "";
            }
            objJson = JSONObject.parseObject(objJson.toString());
        }
        //如果obj为json数组
        if (objJson instanceof JSONArray) {
            JSONArray objArray = (JSONArray) objJson;
            for (int i = 0; i < objArray.size(); i++) {
                Object o = objArray.get(i);
                if (reg.contains(".")) {
                    o = analyzeJSONOArrayReg(i, reg, o);
                }
                return getValueByKey(o, reg);
            }
        }
        //如果为json对象
        else if (objJson instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) objJson;
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                String key = entry.getKey();
                if (reg.equals(key)) {
                    return entry.getValue();
                }
                Object o = objJson;
                if (reg.contains(".")) {
                    o = analyzeJSONObjectReg(reg, jsonObject);
                }else {
                    o = find(reg, o, key);
                }
                if (!"".equals(o)) {
                    return o;
                }
            }
        }
        return "";
    }

    /**
     * 分析jsonArray的表达式
     *
     * @param index
     * @param reg
     * @param json
     * @return
     */
    private static Object analyzeJSONOArrayReg(int index, String reg, Object json) {
        int signIndex = reg.indexOf(".");
        if (signIndex > -1) {
            String begin =  reg.substring(0, signIndex);
            String end = reg.substring(signIndex + 1, reg.length());
            Object notRealResult = "";
            if (json instanceof JSONArray) {
                notRealResult = getValueByKey(((JSONArray) json).get(index), begin);
            }
            return getValueByKey(notRealResult, end);
        }
        return json;
    }

    /**
     * 分析jsonObject的表达式
     *
     * @param reg
     * @param json
     * @return
     */
    private static Object analyzeJSONObjectReg(String reg, JSONObject json) {
        int signIndex = reg.indexOf(".");
        if (signIndex > -1) {
            String begin =  reg.substring(0, signIndex);
            String end = reg.substring(signIndex + 1, reg.length());
            Object notRealResult = json;
            notRealResult = getValueByKey(notRealResult, begin);
            return getValueByKey(notRealResult, end);
        }
        return json;
    }

    private static Object find(String reg, Object jsonObject, String key) {
        Object object = "";
        if (jsonObject instanceof JSONObject) {
            object = ((JSONObject) jsonObject).get(key);
        }
        //如果得到的是数组
        if (object instanceof JSONArray) {
            JSONArray objArray = (JSONArray) object;
            return getValueByKey(objArray, reg);
        }
        //如果key中是一个json对象
        else if (object instanceof JSONObject) {
            return getValueByKey(object, reg);
        }
        return "";
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
