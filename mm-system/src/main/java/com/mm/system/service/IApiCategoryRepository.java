package com.mm.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mm.system.domain.ApiCategoryPO;
import com.mm.system.domain.query.ApiCategoryPageQuery;


/**
 * @author:
 * Date: 2023/4/3 11:02
 * Desc:
 */
public interface IApiCategoryRepository extends IService<ApiCategoryPO> {
    IPage<ApiCategoryPO> queryPage(ApiCategoryPageQuery query);

    /**
     * 名称是否已存在
     * @param name 名称
     * @param excludeId 排除的数据id
     * @return
     */
    boolean nameExist(String name, Long excludeId);
}
