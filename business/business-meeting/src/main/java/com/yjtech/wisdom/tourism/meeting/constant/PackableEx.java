package com.yjtech.wisdom.tourism.meeting.constant;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}
