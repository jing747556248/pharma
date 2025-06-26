package com.sanofi.pharma.common.exception;

import com.sanofi.pharma.common.dto.Error;
import com.sanofi.pharma.common.dto.RespBody;
import com.sanofi.pharma.common.dto.RespCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.*;
import java.util.concurrent.CompletableFuture;


/**
 * @author lijin
 * @since 2025-06-22
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.sanofi.pharma.core")
public class GlobalExceptionHandler {

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RespBody<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                              HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestUri, e.getMethod());
        return RespBody.fail(RespCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BizException.class)
    public RespBody<Void> handleServiceException(BizException e, HttpServletRequest request, HttpServletResponse response) {
        log.error("发生业务异常：", e);
        response.setStatus(Integer.parseInt(RespCode.INTERNAL_SERVER_ERROR.getCode()));
        return RespBody.fail(e.getRespCode());
    }

    /**
     * 请求参数类型不匹配
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public RespBody<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request, HttpServletResponse response) {
        String requestUri = request.getRequestURI();
        log.error("请求参数类型不匹配'{}',发生系统异常.", requestUri);
        response.setStatus(Integer.parseInt(RespCode.BAD_REQUEST.getCode()));
        return RespBody.fail(RespCode.BAD_REQUEST);
    }

    /**
     * 找不到路由
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public RespBody<Void> handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}'不存在.", requestUri);
        return RespBody.fail(RespCode.URI_NOT_FOUND);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public RespBody<Void> handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        String requestUri = request.getRequestURI();
        response.setStatus(Integer.parseInt(RespCode.INTERNAL_SERVER_ERROR.getCode()));
        log.error("请求地址'{}',发生系统异常.", requestUri, e);
        return RespBody.fail(RespCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 参数校验不通过异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespBody<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验不通过: {}", e.getMessage(), e);
        Map<String, String> errors = new HashMap<>();
        List<Error> errorList = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            Error errorDto = new Error();
            errorDto.setCode(RespCode.BAD_REQUEST.getCode());
            errorDto.setTitle(RespCode.BAD_REQUEST.getTitle());
            errorDto.setDetail(errorMessage);
            errorList.add(errorDto);
        });
        return RespBody.fail(errorList);
    }

}
