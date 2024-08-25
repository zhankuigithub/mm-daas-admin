package com.mm.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mm.system.domain.DatasourceInfoPO;
import com.mm.system.domain.query.DataSourceQuery;


public interface IDataSourceRepository extends IService<DatasourceInfoPO> {

    boolean nameExist(String name, Long excludeId);

    IPage<DatasourceInfoPO> queryPage(DataSourceQuery query);
}
