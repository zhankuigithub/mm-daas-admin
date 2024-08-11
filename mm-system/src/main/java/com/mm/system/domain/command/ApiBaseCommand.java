package com.mm.system.domain.command;

import com.mm.system.domain.enums.CheckGroupEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 接口信息对象 api_base
 *
 * @author @date 2023-03-30
 */
@Data
@ApiModel(value = "ApiBase对象", description = "api信息对象")
public class ApiBaseCommand {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空", groups = CheckGroupEnum.Modify.class)
    private Long id;

    /** api名称 */
    private String apiName;

    /** api路径 */
    private String apiPath;

    /**请求方法 1 get 2post 3put 4delete */
    private Integer reqMethod;

    /** 响应数据格式类型 */
    private String respType;

    /** api参数 */
    private List<ApiParamCommand> apiParams;

    /** api返回值 */
    private List<ApiParamCommand> apiReturns;

    /** 描述 */
    private String apiDescription;

    /** 执行sql */
    private String templateSql;

    /** （修改时必须填写注） */
    private String remark;

    /** 状态 0启用 1停用 */
    private Integer apiStatus;

    /** 对应数据源id */
    private Long dataSourceId;

    /** 数据源表名 */
    private String sourceTableName;

    /** 主表名称 */
    private String mainTableName;

    /** 业务类型id */
    private Long categoryId;

    /** 操作类型：CRUD */
    private String execType;

    /** 是否开启驼峰式返回 */
    private Integer openHump;
}
