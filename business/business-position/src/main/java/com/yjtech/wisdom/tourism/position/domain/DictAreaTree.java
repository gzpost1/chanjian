package com.yjtech.wisdom.tourism.position.domain;

import com.yjtech.wisdom.tourism.position.entity.TbDictAreaEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
public class DictAreaTree {
    /**
     * 子树
     */
    private List<DictAreaTree> child;

    /**
     * 数据
     */
    private TbDictAreaEntity data;

    public DictAreaTree() {
        child = new ArrayList<>();
        data = null;
    }
}
