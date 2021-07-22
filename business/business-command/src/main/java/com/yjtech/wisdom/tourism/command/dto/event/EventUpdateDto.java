package com.yjtech.wisdom.tourism.command.dto.event;

import com.yjtech.wisdom.tourism.common.validator.EnumValue;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
*
* @author wuyongchong
* @since 2021-02-22
*/
@Getter
@Setter
public class EventUpdateDto implements Serializable {
    /**
    * id
    */
    @NotNull(message = "id不能为空")
    private Long id;


    /**
    * 事件等级（数据字典）
    */
    @NotBlank(message = "事件等级不能为空")
    @EnumValue(values = {"1", "2","3", "4"})
    private String eventLevel;



    /**
    * 处理单位
    */
    @NotBlank(message = "处理单位不能为空")
    @Length(max = 30,message = "处理单位长度必须小于30位")
    private String handleDepartment;

    /**
    * 处理人员
    */
    @NotBlank(message = "处理人员不能为空")
    @Length(max = 30,message = "处理人员长度必须小于30位")
    private String handlePersonnel;

    /**
    * 处理日期
    */
    @NotNull(message = "处理日期不能为空")
    private LocalDate handleDate;


    /**
     * 处理结果
     */
    @NotBlank(message = "处理结果不能为空")
    @Length(max = 500,message = "处理单位长度必须小于500位")
    private String handleResults;
}
