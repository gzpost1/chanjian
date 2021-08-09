package com.yjtech.wisdom.tourism.systemconfig.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author 李波
 * @description: 菜单数据-图表数据详细
 * @date 2021/7/416:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuChartDetailDto implements Serializable {
    /**
     * 图表id
     */
    private Long chartId;

    /**
     * 图表名称
     */
    private String name;

    /**
     * img_url 大屏缩略图
     */
    private String imgUrl;

    /**
     * 点位坐标[0,1,2]
     */
    @NotEmpty(message = "点位坐标不能为空")
    private List<Integer> pointDatas;

}
