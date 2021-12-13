package com.yjtech.wisdom.tourism.common.bean.zlmedia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 流发现 查询VO
 *
 * @date 2021/9/18 14:54
 * @author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZlmediaResult implements Serializable {

    private static final long serialVersionUID = 1422279487036130043L;
    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 关闭标识
     */
    private Boolean close;

    public static ZlmediaResult success() {
        return new ZlmediaResult(0,"success", null);
    }

    public static ZlmediaResult fail() {
        return new ZlmediaResult(-1,"fail", null);
    }

    public static ZlmediaResult successClose() {
        return new ZlmediaResult(0,"success", true);
    }

    public static ZlmediaResult failClose() {
        return new ZlmediaResult(-1,"fail", false);
    }

}
