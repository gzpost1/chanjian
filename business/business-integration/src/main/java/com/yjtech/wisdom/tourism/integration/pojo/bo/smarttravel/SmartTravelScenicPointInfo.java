package com.yjtech.wisdom.tourism.integration.pojo.bo.smarttravel;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: wuyongchong
 * @date: 2020/5/14 19:32
 */
@Data
public class SmartTravelScenicPointInfo implements Serializable {
    private Double longitude;
    private Double latitude;

    @Override
    public String toString(){
        return longitude+""+latitude;
    }
}

