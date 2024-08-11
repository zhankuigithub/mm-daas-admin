package com.mm.system.domain.dto;

import lombok.Data;

/**
 * @author:
 * Date: 2023/4/11 10:08
 * Desc:
 */
@Data
public class DataSourceTypeDTO {
//    枚举名称
    private String typeName;
    //连接协议
    private String protocol;
//    驱动名称
    private String driverClassName;
//    默认jar
    private String defaultJar;
}
