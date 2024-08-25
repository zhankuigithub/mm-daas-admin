package com.mm.system.domain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ApiBasePageQuery对象", description = "apiQuery")
public class ApiBasePageQuery extends PageQuery {

    @ApiModelProperty(value = "接口名称")
    private String apiName;


    @ApiModelProperty(value = "接口url")
    private String apiPath;

    @ApiModelProperty(value = "业务类型id")
    private Long categoryId;

    /**
     * 对应数据源id
     */
    private Long dataSourceId;

    private Long relServiceId;

    @ApiModelProperty(value = "接口类型")
    private Integer reqMethod;

    private List<Long> ids;

}
