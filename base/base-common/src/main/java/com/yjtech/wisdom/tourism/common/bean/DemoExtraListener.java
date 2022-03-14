package com.yjtech.wisdom.tourism.common.bean;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 读取单元格的批注
 *
 * @author Jiaju Zhuang
 **/
@Slf4j
public class DemoExtraListener implements ReadListener<DemoExtraData> {

    @Override
    public void invoke(DemoExtraData data, AnalysisContext context) {}

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {}

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        switch (extra.getType()) {
            case MERGE:
                log.info(
                    "额外信息是超链接,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{}",
                    extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
                    extra.getLastColumnIndex());
                break;
            default:
        }
    }
}
