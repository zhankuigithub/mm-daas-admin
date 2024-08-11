package com.mm.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mm.system.domain.query.ApiCategoryPageQuery;
import com.mm.system.domain.ApiCategoryPO;
import com.mm.system.mapper.ApiCategoryMapper;
import com.mm.system.service.IApiCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * api类目Service业务层处理
 *
 * @author
 * @date 2023-04-03
 */
@Service
@RequiredArgsConstructor
public class ApiCategoryRepositoryImpl extends ServiceImpl<ApiCategoryMapper, ApiCategoryPO> implements IApiCategoryRepository {

    private final ApiCategoryMapper apiCategoryMapper;

    @Override
    public IPage<ApiCategoryPO> queryPage(ApiCategoryPageQuery query) {
        return page(new Page<ApiCategoryPO>(query.getCurPagerNo(), query.getPageSize()), Wrappers.
                <ApiCategoryPO>lambdaQuery().likeRight(StringUtils.isNotBlank(query.getCategoryName()),ApiCategoryPO::getCategoryName, query.getCategoryName()));
    }

    @Override
    public boolean nameExist(String name, Long excludeId) {
        return apiCategoryMapper.nameExist(name, excludeId) != null;
    }
}
