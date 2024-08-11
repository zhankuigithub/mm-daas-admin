package com.mm.system.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 平台服务表
 * </p>
 *
 * @author
 * @since 2023-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("platform_service")
@ApiModel(value = "PlatformService对象", description = "平台服务表")
public class PlatformServicePO extends BasePO<PlatformServicePO> implements Serializable {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务类型 0不限 1内网 2外网
     */
    private int serviceType;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 安全等级
     */
    private Integer safetyLevel;

    /**
     * 服务状态 0正常 1停用 2暂停
     */
    private int serviceStatus;

    /**
     * 服务限制 0免费 1付费 2限制
     */
    private int restriction;

    /**
     * 服务描述
     */
    private String serviceDesc;

    /**
     * 调用限制
     */
    private Long limitTotal;

    /**
     * 版本号
     */
    private String version;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;


}
