package com.sanofi.pharma.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass  // 关键注解
@Schema(description = "entity基础信息")
public class EntityBase implements Serializable {

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 最近修改人
     */
    @Schema(description = "最近修改人")
    private String updateBy;

    /**
     * 最近修改时间
     */
    @Schema(description = "最近修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 逻辑删除 0 未删除 1 已删除
     */
    @Schema(description = "逻辑删除 0 未删除 1 已删除")
    private Boolean isDeleted;
}
