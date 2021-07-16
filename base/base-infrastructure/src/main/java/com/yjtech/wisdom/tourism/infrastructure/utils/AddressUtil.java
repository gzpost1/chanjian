package com.yjtech.wisdom.tourism.infrastructure.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuhong
 */
public class AddressUtil {
    /**
     * 解析地址
     *
     * @param address
     */
    public static List<Map<String, String>> addressResolution(String address) {
        String regex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?区|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
        Matcher m = Pattern.compile(regex).matcher(address);
        String province = null, city = null, county = null, town = null, village = null;
        List<Map<String, String>> table = new ArrayList<Map<String, String>>();
        Map<String, String> row = null;
        while (m.find()) {
            row = new LinkedHashMap<String, String>();
            province = m.group("province");
            row.put("province", province == null ? "" : province.trim());
            city = m.group("city");
            row.put("city", city == null ? "" : city.trim());
            county = m.group("county");
            row.put("county", county == null ? "" : county.trim());
            town = m.group("town");
            row.put("town", town == null ? "" : town.trim());
            village = m.group("village");
            row.put("village", village == null ? "" : village.trim());
            table.add(row);
        }
        return table;
    }

    public static String getDistrict(String address) {
        List<Map<String, String>> result = addressResolution(address);
        if (CollectionUtils.isEmpty(result))
            return "";

        Map<String, String> map = result.get(0);
        if (StringUtils.isNotBlank(map.get("county")))
            return map.get("county");

        if (StringUtils.isNotBlank(map.get("city")))
            return map.get("city");

        if (StringUtils.isNotBlank(map.get("province")))
            return map.get("province");

        return "";
    }

    public static Map<String, String> getAddressMap(String address) {
        List<Map<String, String>> result = addressResolution(address);
        return CollectionUtils.isEmpty(result) ? new HashMap<>() : result.get(0);
    }

    public static String getProvinceCityCounty(String address) {
        Map<String, String> map = getAddressMap(address);
        return map.get("province") + map.get("city") + map.get("county");
    }

    public static String getProvince(String address) {
        Map<String, String> map = getAddressMap(address);
        return map.get("province");
    }

    public static String getCity(String address) {
        Map<String, String> map = getAddressMap(address);
        return map.get("city");
    }


    public static String getNonProvinceCityCounty(String address) {
        Map<String, String> map = getAddressMap(address);
        return map.get("town") + map.get("village");
    }

    public static void main(String[] args) {
        System.err.println(getAddressMap("北京市昌平区小汤山镇新世纪商城附近"));
        System.err.println(getAddressMap("山西省吕梁市汾阳市营盘街西50米"));
        System.err.println(getProvinceCityCounty("北京市昌平区小汤山镇新世纪商城附近"));
        System.err.println(getProvinceCityCounty("山西省吕梁市汾阳市营盘街西50米"));
        System.err.println(getNonProvinceCityCounty("北京市昌平区小汤山镇新世纪商城附近"));
        System.err.println(getNonProvinceCityCounty("山西省吕梁市汾阳市营盘街西50米"));
        System.err.println(getNonProvinceCityCounty("北京市昌平区小汤山镇新世纪商城附近"));
        System.err.println(getNonProvinceCityCounty("山西省吕梁市汾阳市营盘街西50米"));
        System.err.println(getNonProvinceCityCounty("营盘街西50米"));
    }
}
