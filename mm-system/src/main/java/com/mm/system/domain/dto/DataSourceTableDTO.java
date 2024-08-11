package com.mm.system.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author:
 * Date: 2023/3/29 17:28
 * Desc:
 */
@Data
@Accessors(chain = true)
public class DataSourceTableDTO {
    private String tableName;
    private String desc;
}
