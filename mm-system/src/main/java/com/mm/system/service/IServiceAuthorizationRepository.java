package com.mm.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mm.system.domain.ServiceAuthorizationPO;
import com.mm.system.domain.dto.ServiceAuthorizationDTO;
import com.mm.system.domain.query.ServiceAuthorizationPageQuery;

/**
 * 服务授权Service接口
 *
 * @author
 * @date 2023-04-03
 */
public interface IServiceAuthorizationRepository extends IService<ServiceAuthorizationPO> {

    /**
     * <p> 分页查询 </p>
     *
     * @param query ServiceAuthorizationPageQuery
     * @return IPage
     **/
    IPage<ServiceAuthorizationDTO> queryPage(ServiceAuthorizationPageQuery query);
}
