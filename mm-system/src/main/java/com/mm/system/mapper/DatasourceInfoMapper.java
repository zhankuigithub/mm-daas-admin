package com.mm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mm.system.domain.DatasourceInfoPO;
import com.mm.system.domain.query.DataSourceQuery;
import org.apache.ibatis.annotations.Param;


public interface DatasourceInfoMapper extends BaseMapper<DatasourceInfoPO> {

    /**
     * pan
     * @param name 名称
     * @param excludeId 排除id
     * @return
     */
    Long nameExist(@Param("name") String name, @Param("excludeId") Long excludeId);

    IPage<DatasourceInfoPO> queryPage(Page<DatasourceInfoPO> page, @Param("query") DataSourceQuery query);
}
