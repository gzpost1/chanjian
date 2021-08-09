package com.yjtech.wisdom.tourism.systemconfig.architecture.dto;

import com.yjtech.wisdom.tourism.dto.area.AreaTreeNode;
import com.yjtech.wisdom.tourism.dto.area.TreeNode;
import com.yjtech.wisdom.tourism.systemconfig.menu.vo.SystemconfigMenuDatavlDto;
import lombok.Data;

/**
 * @author 李波
 * @description:
 * @date 2021/7/30 20:24
 */
@Data
public class MenuTreeNode extends TreeNode<AreaTreeNode> {
    /**
     * 是否启用模拟数据(0:否,1:是)
     */
    private Byte isSimulation;

    /**
     * 页面id
     */
    private Long pageId;

    /**
     * 是否展示 0否 1是
     */
    private Byte isShow;

    /**
     * 序号
     */
    private Integer sortNum;

    /**
     * 页面配置
     */
    private SystemconfigMenuDatavlDto pageData;
}
