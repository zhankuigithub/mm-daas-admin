package com.mm.system.domain.command;

import com.mm.system.domain.enums.CheckGroupEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class ApiDocCommand {

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空", groups = {CheckGroupEnum.Modify.class})
    private Long id;

    /** 所属接口 */
    @NotNull(message = "接口id不能为空")
    private Integer interfaceId;

    /** 接口地址 */
    @NotBlank(message = "接口地址不能为空")
    private String reqUrl;

    /** 请求方式(1;get;2:post) */
    @NotNull(message = "请求方式不能为空")
    private Integer reqType;

    /** 请求示例 */
    @NotBlank(message = "请求示例不能为空")
    private String reqEx;

    /** 请求参数示例 */
    private String reqParamEx;

    /** 返回参数示例 */
    private String resParamEx;

    /** 接口备注 */
    @NotBlank(message = "接口备注不能为空")
    private String remark;
}
