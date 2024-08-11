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
 * 接口日志
 * </p>
 *
 * @author
 * @since 2023-04-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("api_log")
@ApiModel(value = "ApiLogPO对象", description = "接口文档")
public class ApiLogPO extends BasePO<ApiDocPO> {

    private static final long serialVersionUID = 1L;

    /** 接口路径 */
    @TableField("api_path")
    private String apiPath;

    /** ip地址 */
    @TableField("ip_address")
    private String ipAddress;

    /** 请求参数 */
    @TableField("req_param")
    private String reqParam;

    /** 应用id */
    @TableField("app_id")
    private Long appId;

    /** 应用账号 */
    @TableField("app_account")
    private String appAccount;

    /** 服务id */
    @TableField("service_id")
    private Long serviceId;

    /** 服务名 */
    @TableField("service_name")
    private String serviceName;

    /** 结果：结果：200成功、500失败 */
    @TableField("result")
    private Integer result;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
