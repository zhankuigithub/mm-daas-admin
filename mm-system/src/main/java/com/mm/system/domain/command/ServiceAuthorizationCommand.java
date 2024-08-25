package com.mm.system.domain.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mm.system.domain.enums.CheckGroupEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@ApiModel(value = "serviceAuthorization对象", description = "服务授权对象")
public class ServiceAuthorizationCommand {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空", groups = CheckGroupEnum.Modify.class)
    private Long id;

    @ApiModelProperty(name = "服务Id")
    @NotNull(message = "服务Id不能为空")
    private Long serviceId;

    @ApiModelProperty("应用id")
    @NotNull(message = "应用id不能为空")
    private Long appId;

    @ApiModelProperty("服务类型 0不限 1内网 2外网 ")
    private Integer serviceType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("开始时间")
    @NotNull(message = "开始时间不能为空")
    private Date beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("结束时间")
    @NotNull(message = "结束时间不能为空")
    private Date endTime;

    @ApiModelProperty("服务限制 0免费 1付费")
    private Integer restriction;

    @ApiModelProperty("授权状态 0正常 1停用 2暂停")
    private int authorizationStatus;

    @ApiModelProperty(value = "鉴权开关：1 开启  0不开启")
    private int openAuth;

    @ApiModelProperty("调用限制")
    @Min(value = 1, message = "调用限制不可以小于1")
    private Long limitTotal;

    private String trustList;
}
