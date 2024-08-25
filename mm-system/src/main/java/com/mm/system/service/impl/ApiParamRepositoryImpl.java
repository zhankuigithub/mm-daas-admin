package com.mm.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mm.system.domain.ApiParamPO;
import com.mm.system.mapper.ApiParamMapper;
import com.mm.system.service.IApiParamRepository;
import org.springframework.stereotype.Service;


@Service
public class ApiParamRepositoryImpl extends ServiceImpl<ApiParamMapper, ApiParamPO> implements IApiParamRepository {

    @Override
    public void removeByApiId(Long apiId) {
        remove(Wrappers.<ApiParamPO>lambdaQuery()
                .eq(ApiParamPO::getApiId, apiId)
              );
    }
}
