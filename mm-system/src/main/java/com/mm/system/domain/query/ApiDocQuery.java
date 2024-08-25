package com.mm.system.domain.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ApiDocQuery extends PageQuery {

    @ApiModelProperty(value = "接口id")
    private Long interfaceId;
}
