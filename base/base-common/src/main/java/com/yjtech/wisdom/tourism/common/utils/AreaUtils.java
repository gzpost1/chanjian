package com.yjtech.wisdom.tourism.common.utils;

import com.yjtech.wisdom.tourism.common.constant.Constants;

import java.util.Objects;

/**
 * 区域信息 工具类
 *
 * @Date 2020/11/2 16:04
 * @Author horadirm
 */
public class AreaUtils {

    /**
     * 区域编码长度
     */
    private static final int AREA_CODE_LENGTH = 12;

    /**
     * 占位符0
     */
    private static final String PLACEHOLDER_ZERO = "0";

    /**
     * 根据层级截取区域信息
     *
     * @param areaCode
     * @param areaLevel
     * @return
     */
    public static String cutAreaByLevel(String areaCode, Integer areaLevel){
        if(StringUtils.isBlank(areaCode) || Objects.isNull(areaLevel) || Constants.NumberConstants.NUMBER_ONE > areaLevel){
            return areaCode;
        }
        else {
            return areaCode.substring(0, 2 * areaLevel);
        }
    }

    /**
     * 截断areaCode至非0位
     * @param areaCode
     * @return
     */
    public static String trimCode(String areaCode) {
        if (StringUtils.isBlank(areaCode)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(areaCode.trim());
        int i = sb.length() - 1;
        while (sb.charAt(i) == '0') {
            i--;
        }
        sb.setLength(i + 1);

        String code = sb.toString();

        if (code.length() % 2 != 0) {
            return code + "0";
        }

        return code;
    }

    public static Boolean isChild(String areaCode,String subAreaDode){
        if(org.apache.commons.lang3.StringUtils.isBlank(subAreaDode)){
            return  false;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(areaCode)){
            return  true;
        }
        return subAreaDode.startsWith(trimCode(areaCode));
    }

    /**
     * 获取原始区域编码（末尾补0）
     * @param processedAreaCode
     * @return
     */
    public static String getOriginalCode(String processedAreaCode) {
        if (StringUtils.isBlank(processedAreaCode)) {
            return processedAreaCode;
        }

        StringBuilder stringBuilder = new StringBuilder(processedAreaCode);
        //区域编码长度不足12，则补0
        while (AREA_CODE_LENGTH > stringBuilder.length()){
            stringBuilder.append(PLACEHOLDER_ZERO);
        }
        return stringBuilder.toString();
    }

    /**
     * 构建区域信息
     * @param provinceId
     * @param cityId
     * @param districtId
     * @return
     */
    public static String buildAreaCode(Object provinceId, Object cityId, Object districtId){
        return Objects.isNull(districtId) ? Objects.isNull(cityId) ? Objects.isNull(provinceId) ? "" : AreaUtils.getOriginalCode(provinceId.toString()) : AreaUtils.getOriginalCode(cityId.toString()) : AreaUtils.getOriginalCode(districtId.toString());
    }

}
