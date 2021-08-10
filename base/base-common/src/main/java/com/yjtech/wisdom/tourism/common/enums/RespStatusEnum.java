package com.yjtech.wisdom.tourism.common.enums;

/**
 * 返回状态
 *
 * @Date 2020/11/17 15:29
 * @Created by libo
 */
public enum RespStatusEnum {
    //成功
    SUCCESS("000000"),
    //失败
    FAILURE("000001"),
    //错误
    ERROR("100000");


    private String code;

    RespStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
