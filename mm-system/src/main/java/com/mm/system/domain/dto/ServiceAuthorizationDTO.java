package com.mm.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
@ApiModel(value = "serviceAuthorization对象", description = "服务授权对象")
public class ServiceAuthorizationDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(name = "应用id")
    private Long appId;

    @ApiModelProperty(name = "服务Id")
    private Long serviceId;

    @ApiModelProperty(name = "服务名称")
    private String serviceName;

    @ApiModelProperty("应用id")
    private Long useId;

    @ApiModelProperty("服务类型 0不限 1内网 2外网 ")
    private Integer serviceType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("开始时间")
    private Date beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("服务限制 0免费 1付费")
    private Integer restriction;

    @ApiModelProperty("信任列表(白名单)")
    private String trustList;

    @ApiModelProperty("授权码")
    private String authorizationCode;

    @ApiModelProperty("授权状态 0正常 1停用 2暂停")
    private Integer authorizationStatus;

    @ApiModelProperty("调用限制")
    private Long limitTotal;

    @ApiModelProperty("鉴权开关：1 开启  0不开启")
    private Integer openAuth;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;
}
