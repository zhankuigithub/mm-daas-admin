package com.mm.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mm.system.domain.enums.DataSourceTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("datasource_info")
@ApiModel(value = "DatasourceInfo对象", description = "动态数据源对象")
public class DatasourceInfoPO extends BasePO<DatasourceInfoPO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据库ip")
    @TableField("db_ip")
    private String dbIp;

    @ApiModelProperty(value = "数据源名称")
    @TableField("data_source_name")
    private String dataSourceName;

    @ApiModelProperty(value = "数据库端口")
    @TableField("db_port")
    private String dbPort;

    @ApiModelProperty(value = "数据库账号")
    @TableField("db_user_name")
    private String dbUserName;

    @ApiModelProperty(value = "数据库密码")
    @TableField("db_password")
    private String dbPassword;

    @ApiModelProperty(value = "数据库名")
    @TableField("db_name")
    private String dbName;

    @ApiModelProperty(value = "数据源url")
    @TableField("db_url")
    private String dbUrl;

    @ApiModelProperty(value = "数据源驱动")
    @TableField("db_driver")
    private String dbDriver;

    @ApiModelProperty(value = "最小连接数")
    @TableField("min_size")
    private Integer minSize;

    @ApiModelProperty(value = "最大连接数")
    @TableField("max_size")
    private Integer maxSize;

    @ApiModelProperty(value = "默认连接数")
    @TableField("init_size")
    private Integer initSize;

    @ApiModelProperty(value = "数据库类型")
    @TableField("type")
    private DataSourceTypeEnum type;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除， 0. 正常、1. 删除")
    @TableField("logic_del")
    @TableLogic
    @JsonIgnore
    private Integer logicDel;

}
