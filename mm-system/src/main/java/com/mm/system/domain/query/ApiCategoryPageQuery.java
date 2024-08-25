package com.mm.system.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class ApiCategoryPageQuery extends PageQuery {
   private String categoryName;
}
