package com.mm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mm.system.domain.ApiDocPO;
import com.mm.system.mapper.ApiDocMapper;
import com.mm.system.service.IApiDocRepository;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 接口文档 服务实现类
 * </p>
 *
 * @author
 * @since 2023-04-07
 */
@Service
public class IApiDocRepositoryImpl extends ServiceImpl<ApiDocMapper, ApiDocPO> implements IApiDocRepository {
}
