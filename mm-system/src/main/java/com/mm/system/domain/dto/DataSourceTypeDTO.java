package com.mm.system.domain.dto;

import lombok.Data;


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
