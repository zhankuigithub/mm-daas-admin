package com.mm.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;



@Data
@ApiModel(value = "ApiBaseDTO对象", description = "api信息对象")
public class ApiBaseDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * api名称
     */
    private String apiName;

    /**
     * api路径
     */
    private String apiPath;

    /**
     * 请求方法 1 get 2post 3put 4delete
     */
    private Integer reqMethod;

    /**
     * 响应数据格式类型
     */
    private String respType;


    /**
     * 描述
     */
    private String apiDescription;

    /**
     * 执行sql
     */
    private String templateSql;

    /**
     * 服务id
     */
    private Long serviceId;

    /**
     * 状态 0启用 1停用
     */
    private Integer apiStatus;

    /**
     * 对应数据源id
     */
    private Long dataSourceId;


    /**
     * 数据源表名
     */
    private String sourceTableName;

    /**
     * 主表名称
     */
    private String mainTableName;

    /**
     * 业务类型id
     */
    private Long categoryId;

    /**
     * api参数
     */
    private List<ApiParamDTO> apiParams;
    /**
     * api返回值
     */
    private List<ApiParamDTO> apiReturns;

    /**
     * 操作类型：CRUD
     */
    private String execType;

    @ApiModelProperty(value = "驼峰式返回")
    private Integer openHump;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "接口文档")
    private ApiDocDTO doc;
}
