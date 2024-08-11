package com.mm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mm.system.domain.ServiceApiRelationPO;

import java.util.List;

/**
 * <p>
 * 服务接口关系表 服务类
 * </p>
 *
 * @author
 * @since 2023-04-07
 */
public interface IServiceApiRelationRepository extends IService<ServiceApiRelationPO> {

    /**
     * <p> 批量编辑 </p>
     *
     * @param apiIds   接口id数组
     * @param serverId 服务id
     **/
    void modifyBatch(List<Long> apiIds, Long serverId);

    /**
     * <p> 通过服务器id获取apiId列表 </p>
     *
     * @param serverId 服务器id
     * @return {@link List }<{@link Long }>
     */
    List<Long> listApiIdByServerId(Long serverId);

    /**
     * <p> 通过apiId获取列表 </p>
     *
     * @param apiIds api id
     * @return {@link List }<{@link ServiceApiRelationPO }>
     */
    List<ServiceApiRelationPO> listByApiId(List<Long> apiIds);
}
