package com.mm.system.domain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 平台应用对象 platform_application
 *
 * @author
 * @date 2023-04-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "PlatformApplicationPageQuery", description = "平台应用分页查询")
public class PlatformApplicationPageQuery extends PageQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用名称")
    private String appName;

    @ApiModelProperty(value = "应用账号")
    private String appAccount;
}
