package com.yjtech.wisdom.tourism.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.chat.dto.MessageHistoryQuery;
import com.yjtech.wisdom.tourism.chat.entity.ChatMessageEntity;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.project.ProjectDataStatisticsQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatMessageMapper extends BaseMapper<ChatMessageEntity> {

    List<ChatMessageEntity> selectMessageHistory(MessageHistoryQuery messageHistoryQuery);

    /**
     * 查询留言数统计
     *
     * @param companyId
     * @return
     */
    int queryMessageStatistics(@Param("companyId") String companyId);

    /**
     * 查询趋势
     *
     * @param params
     * @return
     */
    List<BaseVO> queryAnalysis(@Param("params") ProjectDataStatisticsQueryVO params);

}
