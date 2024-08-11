package com.mm.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mm.system.domain.dto.ServiceAuthorizationDTO;
import com.mm.system.domain.query.ServiceAuthorizationPageQuery;
import com.mm.system.domain.ServiceAuthorizationPO;
import com.mm.system.mapper.ServiceAuthorizationMapper;
import com.mm.system.service.IServiceAuthorizationRepository;
import org.springframework.stereotype.Service;

/**
 * 服务授权Service业务层处理
 *
 * @author
 * @date 2023-04-03
 */
@Service
public class ServiceAuthorizationRepositoryImpl extends ServiceImpl<ServiceAuthorizationMapper, ServiceAuthorizationPO> implements IServiceAuthorizationRepository {

    @Override
    public IPage<ServiceAuthorizationDTO> queryPage(ServiceAuthorizationPageQuery query) {
        return baseMapper.pageQuery(new Page<>(query.getCurPagerNo(), query.getPageSize()), query);
    }
}
