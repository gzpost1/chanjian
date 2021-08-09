package com.yjtech.wisdom.tourism.decisionsupport.common.util;

import com.yjtech.wisdom.tourism.common.exception.CustomException;

/**
 * 决策辅助占位符处理
 *
 * @author renguangqian
 * @date 2021/8/6 11:21
 */
public class PlaceholderUtils {

    /**
     * 占位符替换方法
     *
     * @param text 话术文本
     * @param keyAndValues 顺序必须是占位符、替换的值,进行交替的形式，例如：${aaa},123,${bbb},456
     * @return
     */
    public static String replace(String text, String...keyAndValues) {
        // 如果是奇数位，则报错key 与 value 不匹配
        if ((keyAndValues.length & 1) == 1) {
            throw new CustomException("PlaceholderUtils.replace()方法中，可变参数必须是key,value,key,value...形式，现在长度为奇数！请检查");
        }

        // 遍历奇数
        for (int i = 0; i < keyAndValues.length; i = i + 2) {
            // 特殊符号处理
            String keyAndValue = keyAndValues[i];
            if (keyAndValue.contains("$")) {
                keyAndValue = keyAndValue.replace("$", "\\$");
            }
            if (keyAndValue.contains("{")) {
                keyAndValue = keyAndValue.replace("{", "\\{");
            }
            if (keyAndValue.contains("}")) {
                keyAndValue = keyAndValue.replace("}", "\\}");
            }

            text = text.replaceAll(keyAndValue, keyAndValues[i + 1]);
        }
        return text;
    }
}
