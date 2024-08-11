package com.mm.system.domain.command;


import com.mm.system.domain.enums.CheckGroupEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 平台服务表
 * </p>
 *
 * @author
 * @since 2023-04-07
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "platformService", description = "平台服务")
public class PlatformServiceCommand implements Serializable {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空", groups = CheckGroupEnum.Modify.class)
    private Long id;

    /**
     * 服务名称
     */
    @ApiModelProperty(value = "服务名称")
    @NotBlank(message = "服务名称不能为空")
    private String serviceName;

    /**
     * 服务类型 0不限 1内网 2外网
     */
    @ApiModelProperty(value = "服务类型 0不限 1内网 2外网")
    private int serviceType;

    /**
     * 安全等级
     */
    @ApiModelProperty(value = "安全等级")
    private Integer safetyLevel;

    /**
     * 服务状态 0正常 1停用 2暂停
     */
    @ApiModelProperty(value = "服务状态 0正常 1停用 2暂停")
    private int serviceStatus;

    /**
     * 服务限制 0免费 1付费 2限制
     */
    @ApiModelProperty(value = "服务限制 0免费 1付费 2限制")
    private int restriction;

    /**
     * 服务描述
     */
    @ApiModelProperty(value = "服务描述")
    private String serviceDesc;

    /**
     * 调用限制
     */
    @ApiModelProperty(value = "调用限制")
    private Long limitTotal;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private String version;


    @ApiModelProperty(value = "接口id")
    private List<Long> apiIds;
}
