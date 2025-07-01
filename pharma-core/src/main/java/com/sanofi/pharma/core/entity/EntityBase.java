package com.sanofi.pharma.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass  // 关键注解
@Schema(description = "base entity")
public class EntityBase implements Serializable {

    @Schema(description = "create by")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "create time")
    private Date createTime;

    @Schema(description = "update by")
    private String updateBy;

    @Schema(description = "update time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Schema(description = "Logical delete (0: not deleted 1: deleted)")
    private Boolean isDeleted;
}
