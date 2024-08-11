package com.mm.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author:
 * Date: 2023/3/28 15:37
 * Desc: 数据源信息
 */
@Data
@Accessors(chain = true)
public class DataSourceInfoDTO {
    private Long id;

    @ApiModelProperty(value = "数据源名称")
    private String dataSourceName;

    @ApiModelProperty(value = "数据库ip")
    private String dbIp;

    @ApiModelProperty(value = "数据库端口")
    private String dbPort;

    @ApiModelProperty(value = "数据库账号")
    private String dbUserName;

    @ApiModelProperty(value = "数据库密码")
    private String dbPassword;

    @ApiModelProperty(value = "数据库名")
    private String dbName;

    @ApiModelProperty(value = "数据源url")
    private String dbUrl;

    @ApiModelProperty(value = "数据源驱动")
    private String dbDriver;

    @ApiModelProperty(value = "最小连接数")
    private Integer minSize;

    @ApiModelProperty(value = "最大连接数")
    private Integer maxSize;

    @ApiModelProperty(value = "默认连接数")
    private Integer initSize;

    @ApiModelProperty(value = "数据库类型")
    private String type;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
