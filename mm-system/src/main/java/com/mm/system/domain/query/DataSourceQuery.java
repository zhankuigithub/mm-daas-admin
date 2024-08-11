package com.mm.system.domain.query;

import com.mm.system.domain.enums.CheckGroupEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 数据源表Query
 * </p>
 *
 * @author
 * @date 2023-03-25
 */
@Data
@ApiModel(value = "DataSourceQuery对象", description = "数据源表Query")
public class DataSourceQuery extends PageQuery {

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

    @ApiModelProperty(value = "最小连接数")
    private Integer minSize;

    @ApiModelProperty(value = "最大连接数")
    private Integer maxSize;

    @ApiModelProperty(value = "默认连接数")
    private Integer initSize;

    @ApiModelProperty(value = "数据库类型 MYSQL,HIVE2,IMPALA,CLICK_HOUSE")
    private String type;

}
