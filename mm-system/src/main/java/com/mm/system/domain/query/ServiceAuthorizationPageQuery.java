package com.mm.system.domain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@ApiModel(value = "serviceAuthorization对象", description = "服务授权对象")
public class ServiceAuthorizationPageQuery extends PageQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务")
    private Long serviceId;

    @ApiModelProperty(value = "应用")
    private Long appId;
}
