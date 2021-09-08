package com.yjtech.wisdom.tourism.resource.venue.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author xulei
 * @create 2020-01-08 14:39
 */
@Getter
@Setter
public class AmapResponse {

    private String status;

    private String count;

    private String info;

    private String infocode;


    private List<AmapPoiResponse> pois ;

    /**
     * 逆地址编码
     */
    private Regeocodes regeocode;

    private List<Geocode> geocodes;
}

