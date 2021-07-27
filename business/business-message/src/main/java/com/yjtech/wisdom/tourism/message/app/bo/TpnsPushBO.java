package com.yjtech.wisdom.tourism.message.app.bo;

import com.tencent.xinge.bean.Environment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Tpns 推送 BO
 *
 * @Date 2021/4/7 16:54
 * @Author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TpnsPushBO implements Serializable {

    private static final long serialVersionUID = 6305410043903092990L;

    /**
     * 推送消息标题
     */
    private String title;

    /**
     * 推送消息内容
     */
    private String content;

    /**
     * 推送token列表
     */
    private ArrayList<String> tokenList;

    /**
     * 推送账号列表
     */
    private ArrayList<String> accountList;

    /**
     * 推送环境
     */
    private String environment = Environment.dev.getName();


    public TpnsPushBO(String title, String content){
        this.title = title;
        this.content = content;
    }

}