package com.mm.system.domain.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ApiBaseIdQuery {

    @ApiModelProperty(value = "ApiId")
    @NotNull(message = "请输入ApiId")
    private Long id;

}
