package com.sanofi.pharma.common.dto;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author lijin
 * @since 2025-06-16
 */
@Data
public class RespBody<T> {
    /**
     * 自定义返回 数据结果集
     */
    private T data;

    /**
     * 错误消息
     */
    private List<Error> errors;

    public static RespBody<Void> ok() {
        return restResult(null, RespCode.OK);
    }

    public static <T> RespBody<T> ok(T data) {
        return restResult(data, RespCode.OK);
    }

    public static <T> RespBody<T> fail(RespCode respCode) {
        return restResult(null, respCode);
    }

    public static <T> RespBody<T> fail(List<Error> errors) {
        return restResult(null, errors);
    }

    public static <T> RespBody<T> fail(T data, RespCode respCode) {
        return restResult(data, respCode);
    }


    private static <T> RespBody<T> restResult(T data, RespCode respCode) {
        RespBody<T> r = new RespBody<>();
        r.setData(data);
        if (!RespCode.OK.getCode().equals(respCode.getCode())) {
            Error error = new Error();
            error.setCode(respCode.getCode());
            error.setTitle(respCode.getTitle());
            error.setDetail(respCode.getDetail());
            r.setErrors(Collections.singletonList(error));
        }
        return r;
    }

    private static <T> RespBody<T> restResult(T data, List<Error> errors) {
        RespBody<T> r = new RespBody<>();
        r.setData(data);
        r.setErrors(errors);
        return r;
    }
}
