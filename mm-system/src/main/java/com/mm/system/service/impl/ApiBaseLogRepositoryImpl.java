package com.mm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mm.system.domain.ApiBaseLogPO;
import com.mm.system.mapper.ApiBaseLogMapper;
import com.mm.system.service.IApiBaseLogRepository;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 动态报表操作日志 服务实现类
 * </p>
 *
 * @author
 * @since 2023-08-29
 */
@Service
public class ApiBaseLogRepositoryImpl extends ServiceImpl<ApiBaseLogMapper, ApiBaseLogPO> implements IApiBaseLogRepository {
}
