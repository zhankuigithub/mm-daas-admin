package com.mm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mm.system.domain.ApiBaseLogPO;
import com.mm.system.mapper.ApiBaseLogMapper;
import com.mm.system.service.IApiBaseLogRepository;
import org.springframework.stereotype.Service;


@Service
public class ApiBaseLogRepositoryImpl extends ServiceImpl<ApiBaseLogMapper, ApiBaseLogPO> implements IApiBaseLogRepository {
}
