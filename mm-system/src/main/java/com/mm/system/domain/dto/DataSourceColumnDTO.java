package com.mm.system.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class DataSourceColumnDTO {
    private String columnName;
    private String columnType;
    private String desc;
}
