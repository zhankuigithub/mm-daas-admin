package com.mm.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mm.system.domain.query.DataSourceQuery;
import com.mm.system.domain.DatasourceInfoPO;
import com.mm.system.mapper.DatasourceInfoMapper;
import com.mm.system.service.IDataSourceRepository;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据源
 * </p>
 *
 * @author
 * @date 2023-03-25
 */
@Service
public class DataSourceRepositoryImpl extends ServiceImpl<DatasourceInfoMapper, DatasourceInfoPO> implements IDataSourceRepository {
    @Override
    public boolean nameExist(String name, Long excludeId) {
        return baseMapper.nameExist(name,excludeId)!=null;
    }

    @Override
    public IPage<DatasourceInfoPO> queryPage(DataSourceQuery query) {
        return baseMapper.queryPage(new Page<>(query.getCurPagerNo(), query.getPageSize()),query);
    }
}
