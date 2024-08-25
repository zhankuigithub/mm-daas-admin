package com.mm.system.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class DataSourceTableDTO {
    private String tableName;
    private String desc;
}
