package com.yjtech.wisdom.tourism.resource.broadcast.entity.dto;

import com.yjtech.wisdom.tourism.common.validator.EnumValue;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author zc
 * @since 2020-09-16
 */
@Getter
@Setter
public class BroadcastCreateDto implements Serializable {
    /**
     * 名称
     */
    @Length(max = 50,message = "名称长度必须小于50位")
    private String name;

    /**
     * 广播类型
     */
    private Byte broadcastType;

    /**
     * 经度
     */
    @Length(max = 20,message = "经度长度必须小于20位")
    private String longitude;

    /**
     * 纬度
     */
    @Length(max = 20,message = "纬度长度必须小于20位")
    private String latitude;

    /**
     * 连接字符串
     */
    @Length(max = 512,message = "连接字符串长度必须小于512位")
    private String connectString;

    /**
     * 关联第三方标识,id或name之类的
     */
    @Length(max = 128,message = "关联第三方标识,id或name之类的长度必须小于128位")
    private String thirdId;

    /**
     * 关联第三方标识的字段类型, 0-字符串, 1-整型
     */
    private Byte thirdIdType;

    /**
     * ip
     */
    @Length(max = 20,message = "ip长度必须小于20位")
    private String ip;

    @Length(max = 512,message = "长度必须小于512位")
    private String place;

    @Length(max = 20,message = "长度必须小于20位")
    private String number;

    /**
     * 状态(0:禁用,1:正常)
     */
    @EnumValue(values = {"0", "1"})
    private Byte status;



}
