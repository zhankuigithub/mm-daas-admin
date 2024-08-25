package com.mm.system.domain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "PlatformServiceQuery", description = "平台服务查询")
public class PlatformServiceQuery {

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

}
