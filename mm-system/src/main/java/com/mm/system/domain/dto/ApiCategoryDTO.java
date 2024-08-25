package com.mm.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
@ApiModel(value = "ApiCategory对象", description = "api类目")
public class ApiCategoryDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 类目名称
     */
    @ApiModelProperty("类目名称")
    private String categoryName;

    /**
     * 类目分类
     */
    @ApiModelProperty("类目分类")
    private Integer categoryType;

    /**
     * 类目排序
     */
    @ApiModelProperty("类目排序")
    private Integer categorySort;


    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

}
