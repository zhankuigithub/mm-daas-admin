package com.mm.system.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("api_param")
@ApiModel(value = "ApiParam对象", description = "接口参数对象")
public class ApiParamPO extends BasePO<ApiParamPO> {
    private static final long serialVersionUID = 1L;

    /**
     * 所属接口
     */
    @ApiModelProperty("所属接口")
    private Long apiId;

    /**
     * 参数类型（1：入参；2：出参）
     */
    @ApiModelProperty("参数类型")
    private Integer paramType;

    /**
     * 参数名称
     */
    @ApiModelProperty("参数名称")
    private String fieldName;

    /**
     * 字段类型
     */
    @ApiModelProperty("字段类型")
    private String fieldType;

    /**
     * 是否必传(1:是；2：否)
     */
    @ApiModelProperty("是否必传(1:是；2：否)")
    private String required;

    @ApiModelProperty("描述")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
