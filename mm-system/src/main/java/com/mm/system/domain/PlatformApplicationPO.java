package com.mm.system.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 平台应用对象 platform_application
 *
 * @author
 * @date 2023-04-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("platform_application")
@ApiModel(value = "platformApplication", description = "平台应用")
public class PlatformApplicationPO extends BasePO<PlatformApplicationPO> {
    private static final long serialVersionUID = 1L;

    /** 应用名称 */
    @ApiModelProperty(value = "应用名称")
    private String appName;

    /** 应用账号 */
    @ApiModelProperty(value = "应用账号")
    private String appAccount;

    /** 应用类型 */
    @ApiModelProperty(value = "应用类型")
    private Long appType;

    @ApiModelProperty(value = "密码")
    private String appPwd;

    /** 应用所有人 */
    @ApiModelProperty(value = "应用所有人")
    private String appOwner;

    /** 邮箱账号 */
    @ApiModelProperty(value = "邮箱账号")
    private String appEmail;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;
}
