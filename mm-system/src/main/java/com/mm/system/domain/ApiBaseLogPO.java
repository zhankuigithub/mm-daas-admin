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


/**
 * <p>
 * 接口发布日志
 * </p>
 *
 * @author
 * @date 2023-08-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("api_base_log")
@ApiModel(value = "ApiBaseLogPO对象", description = "接口发布日志")
public class ApiBaseLogPO extends BasePO<ApiBaseLogPO> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "对应报表id")
    private Long apiId;

    @ApiModelProperty(value = "执行sql")
    private String templateSql;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作人id")
    private Long opId;

    @ApiModelProperty(value = "操作人")
    private String opName;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

}
