package com.mm.system.domain.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 接口参数对象 api_param
 *
 * @author
 * @date 2023-04-12
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ApiParam对象", description = "接口参数对象")
public class ApiParamCommand {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 所属接口
     */
    @ApiModelProperty("所属接口")
    private Long apiId;

    /**
     * 参数类型（1：入参；2：出参）
     */
    @ApiModelProperty("参数类型")
    private Integer paramType;

    /**
     * 参数名称
     */
    @ApiModelProperty("参数名称")
    private String fieldName;

    /**
     * 字段类型
     */
    @ApiModelProperty("字段类型")
    private String fieldType;

    /**
     * 是否必传(1:是；2：否)
     */
    @ApiModelProperty("是否必传(1:是；2：否)")
    private String required;

    @ApiModelProperty("描述")
    private String remark;

}
