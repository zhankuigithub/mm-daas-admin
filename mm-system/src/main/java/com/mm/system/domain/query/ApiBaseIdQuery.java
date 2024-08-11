package com.mm.system.domain.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 根据动态id查询
 * </p>
 *
 * @author
 * @date 2023-03-23
 */
@Data
public class ApiBaseIdQuery {

    @ApiModelProperty(value = "ApiId")
    @NotNull(message = "请输入ApiId")
    private Long id;

}
