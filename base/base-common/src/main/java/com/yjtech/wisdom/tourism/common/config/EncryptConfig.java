package com.yjtech.wisdom.tourism.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author oujiangping
 */
@Component
@Data
public class EncryptConfig {
    @Value(value = "${app.dbKey:123}")
    private String key;

    @Value(value = "${app.appKey:!^&afwOpw0Pw1><m}")
    private String appKey;
}
