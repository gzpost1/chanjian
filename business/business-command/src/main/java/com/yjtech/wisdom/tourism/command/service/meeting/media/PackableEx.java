package com.yjtech.wisdom.tourism.command.service.meeting.media;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}
