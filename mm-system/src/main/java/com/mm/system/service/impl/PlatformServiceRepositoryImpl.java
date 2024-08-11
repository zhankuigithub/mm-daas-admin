package com.mm.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mm.system.domain.query.PlatformServicePageQuery;
import com.mm.system.domain.PlatformServicePO;
import com.mm.system.mapper.PlatformServiceMapper;
import com.mm.system.service.IPlatformServiceRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 平台服务Service业务层处理
 *
 * @author
 * @date 2023-04-03
 */
@Service
public class PlatformServiceRepositoryImpl extends ServiceImpl<PlatformServiceMapper, PlatformServicePO> implements IPlatformServiceRepository {

    @Override
    public boolean nameExist(String name, Long excludeId) {
        return baseMapper.nameExist(name, excludeId) != null;
    }

    @Override
    public IPage<PlatformServicePO> pageQuery(PlatformServicePageQuery query) {
        return page(new Page<PlatformServicePO>(query.getCurPagerNo(), query.getPageSize()),
                Wrappers.<PlatformServicePO>lambdaQuery()
                        .like(StringUtils.isNotBlank(query.getServiceName()),
                                PlatformServicePO::getServiceName, query.getServiceName()));
    }
}
