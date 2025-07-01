package com.sanofi.pharma.core.config;

import com.sanofi.pharma.common.exception.BizException;
import com.yahoo.elide.ElideErrors;
import com.yahoo.elide.core.exceptions.ExceptionMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijin
 * @since 2025-06-22
 * Elide框架配置
 */
@Configuration
public class ElideConfiguration {

    @Bean
    public ExceptionMapper<BizException, ElideErrors> bizExceptionMapper() {
        return new BizExceptionMapper();
    }

}