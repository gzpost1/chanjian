package com.yjtech.wisdom.tourism.resource.ticket.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xulei
 * @create 2021-07-03 14:26
 */
@Data
public class TicketQuantityQuery {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endTime;
}
