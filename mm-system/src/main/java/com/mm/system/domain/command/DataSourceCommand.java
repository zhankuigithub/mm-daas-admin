package com.mm.system.domain.command;

import com.mm.system.domain.enums.CheckGroupEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@ApiModel(value = "DataSourceInsertCommand对象")
public class DataSourceCommand {

    @lombok.Getter
    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空", groups = CheckGroupEnum.Modify.class)
    private Long id;

    @ApiModelProperty(value = "数据库ip")
    @NotBlank(message = "数据库ip不能为空")
    private String dbIp;

    @ApiModelProperty(value = "数据源名称")
    @NotBlank(message = "数据源名称不能为空")
    private String dataSourceName;

    @ApiModelProperty(value = "数据库端口")
    @NotBlank(message = "数据库端口不能为空")
    private String dbPort;

    @ApiModelProperty(value = "数据库账号")
    @NotBlank(message = "数据库账号不能为空")
    private String dbUserName;

    @ApiModelProperty(value = "数据库密码，aes加密的密文")
    @NotBlank(message = "数据库密码不能为空")
    private String dbPassword;

    @ApiModelProperty(value = "数据库名")
    @NotBlank(message = "数据库名不能为空")
    private String dbName;

    @ApiModelProperty(value = "数据源url")
    @NotBlank(message = "数据源url不能为空")
    private String dbUrl;

    @ApiModelProperty(value = "数据源驱动")
    @NotBlank(message = "数据源驱动不能为空")
    private String dbDriver;

    @ApiModelProperty(value = "最小连接数")
    private Integer minSize;

    @ApiModelProperty(value = "最大连接数")
    private Integer maxSize;

    @ApiModelProperty(value = "默认连接数")
    private Integer initSize;

    @ApiModelProperty(value = "数据库类型 MYSQL,HIVE2,IMPALA,CLICK_HOUSE")
    private String type;

    @ApiModelProperty(value = "要获取表结构名称")
    private String tableName;

}
