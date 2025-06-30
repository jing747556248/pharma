package com.sanofi.pharma.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.security")
public class SecurityConfigProperties {
    private String origin = "*";
}
