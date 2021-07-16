package com.yjtech.wisdom.tourism.resource.comment.extensionpoint;

import com.yjtech.wisdom.tourism.extension.ExtensionPointI;

import java.util.Map;

/**
 * 定义扩展点
 *
 * @author liuhong
 * @date 2021-07-13 11:08
 */
public interface PraiseWordQryExtPt extends ExtensionPointI {
    public Map<String, Object> queryHotWordBYComments();
}
