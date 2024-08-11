package com.mm.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 接口文档
 * </p>
 *
 * @author
 * @since 2023-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("api_doc")
@ApiModel(value = "ApiDocPO对象", description = "接口文档")
public class ApiDocPO extends BasePO<ApiDocPO> {

    private static final long serialVersionUID = 1L;

    /** 所属接口 */
    @TableField("interface_id")
    private Integer interfaceId;

    /** 接口地址 */
    @TableField("req_url")
    private String reqUrl;

    /** 请求方式(1;get;2:post) */
    @TableField("req_type")
    private Integer reqType;

    /** 请求示例 */
    @TableField("req_ex")
    private String reqEx;

    /** 接口备注 */
    @TableField("remark")
    private String remark;

    /** 请求参数示例 */
    @TableField("req_param_ex")
    private String reqParamEx;

    /** 返回参数示例 */
    @TableField("res_param_ex")
    private String resParamEx;

    /** 状态 */
    @TableField("status")
    private Integer status;

    /** 创建时间 */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 修改时间 */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
