package com.mm.system.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author:
 * Date: 2023/4/3 11:31
 * Desc:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiCategoryPageQuery extends PageQuery {
   private String categoryName;
}
