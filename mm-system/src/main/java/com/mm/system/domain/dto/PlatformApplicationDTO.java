package com.mm.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
@ApiModel(value = "platformApplication", description = "平台应用")
public class PlatformApplicationDTO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    /** 应用名称 */
    @ApiModelProperty(value = "应用名称")
    private String appName;

    /** 应用账号 */
    @ApiModelProperty(value = "应用账号")
    private String appAccount;

    /** 应用类型 */
    @ApiModelProperty(value = "应用类型")
    private Long appType;

    /** 应用所有人 */
    @ApiModelProperty(value = "应用所有人")
    private String appOwner;

    /** 邮箱账号 */
    @ApiModelProperty(value = "邮箱账号")
    private String appEmail;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;
}
