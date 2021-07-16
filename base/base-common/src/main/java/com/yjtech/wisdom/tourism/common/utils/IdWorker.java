package com.yjtech.wisdom.tourism.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;

public class IdWorker {
    private static final Logger log = LoggerFactory.getLogger(IdWorker.class);
    private final long workerId;
    private static final long twepoch = 1483200000000L;
    private long sequence = 0L;
    private static final long workerIdBits = 18L;
    private static final long maxWorkerId = 262143L;
    private static final long sequenceBits = 4L;
    private static final long workerIdShift = 4L;
    private static final long timestampLeftShift = 22L;
    private static final long sequenceMask = 15L;
    private long lastTimestamp = -1L;
    private static IdWorker self = new IdWorker();

    public static IdWorker getInstance() {
        return self;
    }

    private IdWorker() {
        long workerId = -1L;

        try {
            workerId = this.generateWorkerId();
        } catch (Exception var4) {
            log.error("Occur an error when initializing the IdWorker because " + var4.getMessage(), var4);
        }

        if (workerId <= 262143L && workerId >= 0L) {
            this.workerId = workerId;
        } else {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 262143L));
        }
    }

    public synchronized long nextId() {
        long timestamp = this.now();
        if (timestamp < this.lastTimestamp) {
            try {
                throw new Exception(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
            } catch (Exception var5) {
                log.error(var5.getMessage(), var5);
            }
        }

        if (this.lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1L & 15L;
            if (this.sequence == 0L) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0L;
        }

        this.lastTimestamp = timestamp;
        long nextId = (timestamp - 1483200000000L & 2199023255551L) << 22 | this.workerId << 4 | this.sequence;
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp;
        for(timestamp = this.now(); timestamp <= lastTimestamp; timestamp = this.now()) {
        }

        return timestamp;
    }

    private long now() {
        return System.currentTimeMillis();
    }

    private long generateWorkerId() throws Exception {
        String hostAddress = Inet4Address.getLocalHost().getHostAddress();
        byte[] ipBytes = CommonUtil.textToNumericFormatV4(hostAddress);
        return (long)(((ipBytes[1] & 3) << 16) + ((ipBytes[2] & 255) << 8) + (ipBytes[3] & 255));
    }
}

