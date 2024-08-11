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
 * 服务授权对象 service_authorization
 *
 * @author
 * @date 2023-04-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("service_authorization")
@ApiModel(value = "serviceAuthorization对象", description = "服务授权对象")
public class ServiceAuthorizationPO extends BasePO<ServiceAuthorizationPO> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "授权的服务id")
    @TableField(value = "service_id")
    private Long serviceId;

    @ApiModelProperty(value = "应用id")
    @TableField(value = "app_id")
    private Long appId;

    @ApiModelProperty(value = "服务类型 0不限 1内网 2外网")
    @TableField(value = "service_type")
    private int serviceType;

    @ApiModelProperty(value = "开始时间")
    @TableField(value = "begin_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    @ApiModelProperty(value = "结束时间")
    @TableField(value = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty(value = "服务限制 0免费 1付费")
    @TableField(value = "restriction")
    private int restriction;

    @ApiModelProperty(value = "授权编码")
    @TableField(value = "authorization_code")
    private String authorizationCode;

    @ApiModelProperty(value = "信任列表(白名单)")
    @TableField(value = "trust_list")
    private String trustList;

    @ApiModelProperty(value = "授权状态 0正常 1停用 2暂停")
    @TableField(value = "authorization_status")
    private int authorizationStatus;

    @ApiModelProperty(value = "调用限制")
    @TableField(value = "limit_total")
    private Long limitTotal;

    @ApiModelProperty(value = "鉴权开关：1 开启  0不开启")
    @TableField(value = "open_auth")
    private int openAuth;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_by")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField(value = "update_by")
    private String updateBy;

    public boolean getOpenAuth() {
        return openAuth == 1;
    }
}
