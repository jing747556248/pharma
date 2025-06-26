package com.sanofi.pharma.common.dto;

import lombok.Getter;

/**
 * @author lijin
 * @since 2025-06-16
 */
@Getter
public enum RespCode {
    /**
     * Default OK
     */
    OK("200", "OK", "请求成功"),

    /**
     * BAD_REQUEST
     */
    BAD_REQUEST("400", "Bad Request", "参数无效"),
    /**
     * UNAUTHORIZED
     */
    UNAUTHORIZED("401", "Unauthorized", "未经授权的访问,由于凭据无效被拒绝"),
    /**
     * FORBIDDEN
     */
    FORBIDDEN("403", "Forbidden", "请求资源的访问被服务器拒绝"),
    /**
     * URI_NOT_FOUND
     */
    URI_NOT_FOUND("404", "URI NOT FOUND", "请求地址不存在"),
    /**
     * METHOD_NOT_ALLOWED
     */
    METHOD_NOT_ALLOWED("405", "Method Not Allowed", "请求的HTTP方法不允许"),
    /**
     * REQUEST_TIMEOUT
     */
    REQUEST_TIMEOUT("406", "Request Timeout", "请求超时"),

    /**
     * UNSUPPORTED_MEDIA_TYPE
     */
    UNSUPPORTED_MEDIA_TYPE("407", "Unsupported Media Type", "不支持的媒体类型(Content-Type 或 Content-Encoding)"),

    /**
     * MISSING_REQ_HEADER
     */
    MISSING_REQ_HEADER("408", "Missing Request Header", "缺失必要的请求头(Headers)"),

    /**
     * INTERNAL_SERVER_ERROR
     */
    INTERNAL_SERVER_ERROR("500", "Internal Server Error", "服务器内部系统未知异常"),

    SYSTEM_BUSY("501", "System Busy Error", "系统繁忙，请稍后重试"),

    /**
     * NULL_POINTER_EXCEPTION
     */
    NULL_POINTER_EXCEPTION("550", "Null Pointer Exception", "服务器内部空指针异常"),
    /**
     * DATABASE_EXCEPTION
     */
    DATABASE_EXCEPTION("551", "Database Exception", "服务器内部数据库发生异常"),
    /**
     * SQL_EXCEPTION
     */
    SQL_EXCEPTION("552", "Sql Exception", "服务器内部数据库SQL执行异常"),

    PRESCRIPTION_DRUG_LIST_EMPTY("10001", "Prescription Drug List Empty Exception", "处方中没有药品"),
    PRESCRIPTION_DRUG_NOT_EXIST("10002", "Prescription Drug Not Exist Exception", "药房没有处方中的药品"),
    PRESCRIPTION_DRUG_QUANTITY_NOT_ENOUGH("10003", "Prescription Drug Quantity Not Enough Exception", "药房的药品库存不足"),
    PRESCRIPTION_NOT_EXIST("10004", "Prescription Not Exist Exception", "处方不存在"),
    PRESCRIPTION_HAS_BEEN_FULFILLED("10005", "Prescription Has Been Fulfilled Exception", "处方已经执行过了");

    /**
     * 自定义 返回码
     */
    private final String code;
    /**
     * 返回码 描述
     */
    private final String title;
    /**
     * 返回码提示说明
     */
    private final String detail;

    RespCode(String code, String title, String detail) {
        this.code = code;
        this.title = title;
        this.detail = detail;
    }
}