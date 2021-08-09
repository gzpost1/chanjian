package com.yjtech.wisdom.tourism.command.vo.travelcomplaint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 旅游投诉 处理VO
 *
 * @Author horadirm
 * @Date 2021/7/21 17:41
 */
@Data
public class TravelComplaintDealVO extends BaseEntity {

    private static final long serialVersionUID = 9190959961354322730L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 处理人id
     */
    @JsonIgnore
    private Long acceptUserId;

    /**
     * 处理单位
     */
    @NotBlank(message = "处理单位不能为空")
    private String acceptOrganization;

    /**
     * 处理时间
     */
    @NotNull(message = "处理时间不能为空")
    private LocalDate acceptTime;

    /**
     * 处理结果
     */
    @NotBlank(message = "处理结果不能为空")
    private String acceptResult;


}
