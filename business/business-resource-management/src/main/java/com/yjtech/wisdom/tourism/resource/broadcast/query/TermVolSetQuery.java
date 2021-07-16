package com.yjtech.wisdom.tourism.resource.broadcast.query;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zc
 */
@Data
public class TermVolSetQuery {
    @NotNull(message = "终端不存在")
    private List<Long> broadcastIds;

    @Length(min = 1,max = 100,message = "音量必须在100以内")
    private Integer volume;
}
