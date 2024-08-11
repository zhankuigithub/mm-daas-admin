package com.mm.system.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 服务接口关系表
 * </p>
 *
 * @author
 * @since 2023-04-07
 */
@Data
@Accessors(chain = true)
@TableName("service_api_relation")
@ApiModel(value = "ServiceApiRelationPO对象", description = "服务接口关系表")
public class ServiceApiRelationPO extends BasePO<ServiceApiRelationPO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务主键id")
    @TableField(value = "server_id")
    private Long serverId;

    @ApiModelProperty(value = "接口主键id")
    @TableField(value = "api_id")
    private Long apiId;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
