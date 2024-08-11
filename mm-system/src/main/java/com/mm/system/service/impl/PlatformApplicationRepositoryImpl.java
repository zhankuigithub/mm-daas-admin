package com.mm.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mm.system.domain.query.PlatformApplicationPageQuery;
import com.mm.system.domain.PlatformApplicationPO;
import com.mm.system.mapper.PlatformApplicationMapper;
import com.mm.system.service.IPlatformApplicationRepository;
import org.springframework.stereotype.Service;

/**
 * 平台应用Service业务层处理
 *
 * @author
 * @date 2023-04-03
 */
@Service
public class PlatformApplicationRepositoryImpl extends ServiceImpl<PlatformApplicationMapper, PlatformApplicationPO> implements IPlatformApplicationRepository {
    @Override
    public IPage<PlatformApplicationPO> queryPage(PlatformApplicationPageQuery query) {
        return page(new Page<>(query.getCurPagerNo(), query.getPageSize()),
                Wrappers.<PlatformApplicationPO>lambdaQuery().
                        like(StringUtils.isNotBlank(query.getAppName()), PlatformApplicationPO::getAppName, query.getAppName())
                        .like(StringUtils.isNotBlank(query.getAppAccount()), PlatformApplicationPO::getAppAccount, query.getAppAccount()));
    }

    @Override
    public boolean nameExist(String name, Long excludeId) {
        return baseMapper.nameExist(name, excludeId) != null;
    }
}
