package com.mm.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * <p>
 * 接口发布日志
 * </p>
 *
 * @author
 * @date 2023-08-29
 */
@Data
public class ApiBaseLogDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "对应报表id")
    private Long apiId;

    @ApiModelProperty(value = "执行sql")
    private String templateSql;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作人id")
    private String opId;

    @ApiModelProperty(value = "操作人")
    private String opName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
