package com.mm.system.domain.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 接口文档page查询实体
 * </p>
 *
 * @author
 * @date 2023-04-07
 */
@Data
public class ApiDocQuery extends PageQuery {

    @ApiModelProperty(value = "接口id")
    private Long interfaceId;
}
