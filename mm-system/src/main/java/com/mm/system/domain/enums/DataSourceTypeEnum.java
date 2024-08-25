package com.mm.system.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mm.common.utils.BeanConverter;
import com.mm.system.domain.dto.DataSourceTypeDTO;

import java.util.ArrayList;
import java.util.List;


public enum DataSourceTypeEnum {
       MYSQL("mysql", "com.mysql.cj.jdbc.Driver", "mysql-connector-java-8.0.28.jar"),
       HIVE2("hive", "org.apache.hive.jdbc.HiveDriver", "hive-jdbc-2.3.7.jar"),
       IMPALA("impala", "com.cloudera.impala.jdbc.Driver", "ImpalaJDBC42.jar"),
       CLICK_HOUSE("clickhouse", "com.clickhouse.jdbc.ClickHouseDriver", "clickhouse-jdbc-0.2.4.jar");

       @EnumValue
       private String protocol;
       private String driverClassName;
       private String defaultJar;

        private DataSourceTypeEnum(String protocol, String driverClassName, String defaultJar) {
           this.setProtocol(protocol);
           this.setDriverClassName(driverClassName);
           this.setDefaultJar(defaultJar);
       }


       public String getDriverClassName() {
           return this.driverClassName;
       }

       private void setDriverClassName(String driverClassName) {
           this.driverClassName = driverClassName;
       }

       public String getProtocol() {
           return this.protocol;
       }

       private void setProtocol(String protocol) {
           this.protocol = protocol;
       }

       public String getDefaultJar() {
           return this.defaultJar;
       }

       private void setDefaultJar(String defaultJar) {
           this.defaultJar = defaultJar;
       }

       public static List<DataSourceTypeDTO> getDataTypes(){
           List<DataSourceTypeDTO> result= new ArrayList<>();
           for (DataSourceTypeEnum value : values()) {
               DataSourceTypeDTO dataSourceTypeDTO = BeanConverter.toBean(value, DataSourceTypeDTO.class);
               dataSourceTypeDTO.setTypeName(value.name());
               result.add(dataSourceTypeDTO);
           }
           return result;
       }

   }

