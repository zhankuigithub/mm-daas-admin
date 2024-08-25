package com.mm.system.domain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@ApiModel(value = "ApiLogPageQuery对象", description = "报表发布日志")
public class ApiLogPageQuery extends PageQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "接口id")
    private Long apiId;

}
