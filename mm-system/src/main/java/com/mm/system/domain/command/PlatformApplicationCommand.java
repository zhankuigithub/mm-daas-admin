package com.mm.system.domain.command;

import com.mm.system.domain.enums.CheckGroupEnum;
import com.mm.system.domain.enums.PatternEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@Accessors(chain = true)
@ApiModel(value = "platformApplication", description = "平台应用")
public class PlatformApplicationCommand {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空", groups = CheckGroupEnum.Modify.class)
    private Long id;

    /** 应用名称 */
    @ApiModelProperty(value = "应用名称")
    @NotBlank(message = "应用名称不能为空")
    private String appName;

    /** 应用账号 */
    @ApiModelProperty(value = "应用账号")
    @Pattern(regexp = PatternEnum.EN_NUMBER_REG, message = "请确保应用账号不为空且格式正确")
    private String appAccount;

    /** 应用类型 */
    @ApiModelProperty(value = "应用类型")
    private Long appType;

    /** 应用所有人 */
    @ApiModelProperty(value = "应用负责人")
    @Pattern(regexp = PatternEnum.CHINESE_REG, message = "请确保应用负责人不为空且格式正确")
    private String appOwner;

    /** 邮箱账号 */
    @ApiModelProperty(value = "邮箱账号")
    @Email(message = "请确保邮箱账号不为空且格式正确")
    private String appEmail;

}
