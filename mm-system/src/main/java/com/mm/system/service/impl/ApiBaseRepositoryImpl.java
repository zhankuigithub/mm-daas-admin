package com.mm.system.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mm.system.domain.query.ApiBasePageQuery;
import com.mm.system.domain.ApiBasePO;
import com.mm.system.mapper.ApiBaseMapper;
import com.mm.system.service.IApiBaseRepository;
import org.springframework.stereotype.Service;

@Service
public class ApiBaseRepositoryImpl extends ServiceImpl<ApiBaseMapper, ApiBasePO> implements IApiBaseRepository {
    @Override
    public boolean nameExist(Long id, String name) {
        return baseMapper.selectOne(new LambdaQueryWrapper<ApiBasePO>()
                .eq(ApiBasePO::getApiName, name)
                .ne(id != null, ApiBasePO::getId, id)
                .last("limit 1")) != null;
    }

    @Override
    public boolean apiPathExist(Long id, String apiPath) {
        return baseMapper.selectOne(new LambdaQueryWrapper<ApiBasePO>()
                .eq(ApiBasePO::getApiPath, apiPath)
                .ne(id != null, ApiBasePO::getId, id)
                .last("limit 1")) != null;
    }

    @Override
    public Page<ApiBasePO> pageQuery(ApiBasePageQuery query) {
        return page(new Page<>(query.getCurPagerNo(), query.getPageSize()),
                Wrappers.<ApiBasePO>lambdaQuery()
                        .in(CollectionUtils.isNotEmpty(query.getIds()), ApiBasePO::getId, query.getIds())
                        .like(CharSequenceUtil.isNotBlank(query.getApiName()), ApiBasePO::getApiName, query.getApiName())
                        .eq(query.getApiPath() != null, ApiBasePO::getApiPath, query.getApiPath())
                        .eq(query.getReqMethod() != null, ApiBasePO::getReqMethod, query.getReqMethod())
                        .eq(query.getDataSourceId() != null, ApiBasePO::getDataSourceId, query.getDataSourceId())
                        .eq(query.getCategoryId() != null, ApiBasePO::getCategoryId, query.getCategoryId()).orderByDesc(ApiBasePO::getUpdateTime)
        );
    }
}
