package com.yjtech.wisdom.tourism.resource.venue.dto;

import lombok.Data;

@Data
public class Regeocodes {
    String formatted_address;

    AddressComponent addressComponent;

    @Data
    public static class AddressComponent {
        String adcode;
    }
}
