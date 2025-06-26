package com.sanofi.pharma.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lijin
 * @since 2025-06-16
 */
@Data
public class Error implements Serializable {

    // HTTP状态码
//    private String status;

    // 应用级错误码
    private String code;

    // 错误摘要
    private String title;

    // 详细说明
    private String detail;

}
