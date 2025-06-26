package com.sanofi.pharma.common.exception;

import com.sanofi.pharma.common.dto.RespCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * @author lijin
 * @since 2025-06-22
 * 业务异常
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public final class BizException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private RespCode respCode;

    public BizException(RespCode respCode) {
        super(respCode.getDetail());
        this.respCode = respCode;
    }
}
