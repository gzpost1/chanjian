package com.yjtech.wisdom.tourism.resource.ticket.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liuhong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class VisitorSourceInfo {
    private List<CityInfo> inside;

    private List<ProvinceInfo> outside;
}
