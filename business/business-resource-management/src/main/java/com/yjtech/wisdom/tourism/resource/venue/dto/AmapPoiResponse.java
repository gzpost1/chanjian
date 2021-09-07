package com.yjtech.wisdom.tourism.resource.venue.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author xulei
 * @create 2020-01-08 14:43
 */
@Getter
@Setter
public class AmapPoiResponse {

    private String id;

    private List parent ;

    private List<String> childtype ;

    private String name;

    private String tag ;

    private String type;

    private String typecode;

    private String biz_type;

    private String address;

    private String location;

    private String tel;

    private List<String> postcode ;

    private List<String> website ;

    private List<String> email ;

    private String pcode;

    private String pname;

    private String citycode;

    private String cityname;

    private String adcode;

    private String adname;

    private List<String> importance ;

    private List<String> shopid ;

    private String shopinfo;

    private List<String> poiweight ;

    private String gridcode;

    private List<String> distance ;

    private List<String> alias ;

    private String navi_poiid;

    private String entr_location;

    private List<String> exit_location ;

    private String match;

    private String recommend;

    private String timestamp;

    private String indoor_map;

    private String groupbuy_num;

    private List<String> business_area ;

    private String discount_num;

    private AmapBizExt biz_ext;

    private List<String> event ;


    private List<AmapPhotos> photos ;

    private List<String> children ;





}
