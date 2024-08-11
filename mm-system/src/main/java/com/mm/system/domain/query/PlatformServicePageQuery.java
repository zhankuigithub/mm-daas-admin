package com.mm.system.domain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author:
 * Date: 2023/4/7 15:38
 * Desc:
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "PlatformServicePageQuery", description = "平台服务分页查询")
public class PlatformServicePageQuery extends PageQuery{

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "服务包含api")
    private List<Long> apiIds;
}
