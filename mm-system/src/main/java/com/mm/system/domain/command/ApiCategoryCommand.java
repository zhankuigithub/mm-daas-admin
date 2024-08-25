package com.mm.system.domain.command;

import com.mm.system.domain.enums.CheckGroupEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



@Data
@ApiModel(value = "ApiCategory对象", description = "api类目对象")
public class ApiCategoryCommand {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空", groups = CheckGroupEnum.Modify.class)
    private Long id;

    /**
     * 类目名称
     */
    @ApiModelProperty("类目名称")
    @NotBlank(message = "类目名称不能为空")
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

}
