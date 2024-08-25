package com.mm.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class ApiDocDTO {

    /** 主键id */
    private Long id;

    /** 所属接口 */
    private Integer interfaceId;

    /** 接口地址 */
    private String reqUrl;

    /** 请求方式(1;get;2:post) */
    private Integer reqType;

    /** 请求示例 */
    private String reqEx;

    /** 接口备注 */
    private String remark;

    /** 请求参数示例 */
    private String reqParamEx;

    /** 返回参数示例 */
    private String resParamEx;

    /** 状态 */
    private Integer status;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
