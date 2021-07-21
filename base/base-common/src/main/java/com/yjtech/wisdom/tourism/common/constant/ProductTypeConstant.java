package com.yjtech.wisdom.tourism.common.constant;

import com.google.common.collect.Maps;

import java.util.HashMap;

/**
 * @author xulei
 * @create 2020-11-17 16:33
 */
public class ProductTypeConstant {

    public final  static HashMap<Byte,String> ProductTypeMap =Maps.newHashMap();

    static {
        ProductTypeMap.put((byte)1,"票类");
        ProductTypeMap.put((byte)3,"券类");
        ProductTypeMap.put((byte)4,"酒店");
        ProductTypeMap.put((byte)5,"实物类");
        ProductTypeMap.put((byte)-1,"总数");
    }
}
