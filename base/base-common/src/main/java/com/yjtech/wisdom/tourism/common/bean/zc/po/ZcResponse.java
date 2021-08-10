package com.yjtech.wisdom.tourism.common.bean.zc.po;

import com.yjtech.wisdom.tourism.common.enums.RespStatusEnum;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 中测返回
 *
 * @Date 2020/11/20 16:55
 * @Author horadirm
 */
@Data
public class ZcResponse implements Serializable {

    private static final long serialVersionUID = -8621478505873861858L;

    /**
     * 返回码(成功：000000，失败：000001，错误：100000)
     */
    private String code;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 数据总量
     */
    private Integer total;


    public boolean isSuccess(){
        return StringUtils.isNotBlank(getCode()) && RespStatusEnum.SUCCESS.getCode().equals(getCode()) ? true : false;
    }

    public boolean isEmpty(){
        return Objects.isNull(getData()) ? true : false;
    }

}
