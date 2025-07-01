package com.sanofi.pharma.core.config;

import com.sanofi.pharma.common.exception.BizException;
import com.yahoo.elide.ElideErrorResponse;
import com.yahoo.elide.ElideErrors;
import com.yahoo.elide.core.exceptions.ErrorContext;
import com.yahoo.elide.core.exceptions.ExceptionMapper;

/**
 * @author lijin
 * @since 2025-06-22
 * Elide框架抛出来的BizException包装
 */
public class BizExceptionMapper implements ExceptionMapper<BizException, ElideErrors> {

    @Override
    public ElideErrorResponse<? extends ElideErrors> toErrorResponse(BizException exception, ErrorContext errorContext) {
        return ElideErrorResponse.badRequest()
                .errors(errors -> errors.error(error -> error.message(exception.getMessage())
                ));
    }
}
