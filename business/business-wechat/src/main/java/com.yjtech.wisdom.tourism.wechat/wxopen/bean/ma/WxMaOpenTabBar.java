package com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * tabBar对象
 *
 * @author yqx
 * @date 2018/9/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxMaOpenTabBar implements Serializable {

    private String color;

    private String selectedColor;

    private String backgroundColor;

    private String borderStyle;

    private List<WxMaOpenTab> list;

    private String position;
}
