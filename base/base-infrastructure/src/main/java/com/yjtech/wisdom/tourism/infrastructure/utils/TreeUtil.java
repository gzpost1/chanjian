package com.yjtech.wisdom.tourism.infrastructure.utils;

import com.yjtech.wisdom.tourism.dto.area.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 树生成工具
 *
 * @author liuhong
 * @date 2021-07-02 15:05
 */
public class TreeUtil {

    /**
     * 生成树
     */
    public static <T extends TreeNode> List<T> makeTree(List<T> list) {
        List<T> tree = new ArrayList<T>();
        if (null != list) {
            TreeNode parentNode = new TreeNode();
            for (T node : list) {
                parentNode.setId(node.getParentId());
                int index = list.indexOf(parentNode);
                if (index != -1) {
                    (list.get(index)).addChild(node);
                } else {
                    tree.add(node);
                }
            }
        }
        return tree;
    }

}

