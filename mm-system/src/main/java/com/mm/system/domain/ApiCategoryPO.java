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
 * api类目对象 api_category
 *
 * @author
 * @date 2023-04-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("api_category")
@ApiModel(value = "ApiCategory对象", description = "api类目")
public class ApiCategoryPO extends BasePO<ApiCategoryPO>
{
    private static final long serialVersionUID = 1L;

    /** 类目名称 */
    @ApiModelProperty("类目名称")
    private String categoryName;

    /** 类目分类 */
    @ApiModelProperty("类目分类")
    private Integer categoryType;

    /** 类目排序 */
    @ApiModelProperty( "类目排序")
    private Integer categorySort;

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
}
