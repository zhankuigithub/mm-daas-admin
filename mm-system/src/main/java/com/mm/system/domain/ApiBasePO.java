package com.mm.system.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("api_base")
@ApiModel(value = "ApiBase对象", description = "api信息对象")
public class ApiBasePO extends BasePO<ApiBasePO> {
    private static final long serialVersionUID = 1L;

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
     * 操作类型：CRUD
     */
    private String execType;

    @ApiModelProperty(value = "是否开启驼峰命名返回，0-不开启、1-开启")
    @TableField("open_hump")
    private Integer openHump;

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

    @ApiModelProperty(value = "逻辑删除， 0. 正常、1. 删除")
    @TableField("deleted")
    @TableLogic
    @JsonIgnore
    private Integer deleted;

    /**
     * <p> 是否开启驼峰式返回 </p>
     * boolean
     *
     * @return boolean
     **/
    public boolean ifOpenHump() {
        return openHump == 1;
    }
}
