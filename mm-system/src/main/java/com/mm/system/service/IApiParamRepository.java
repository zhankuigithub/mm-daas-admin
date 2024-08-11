package com.mm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mm.system.domain.ApiParamPO;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @date 2023-04-12
 */
public interface IApiParamRepository extends IService<ApiParamPO> {
    /**
     * <p> 通过apiId删除数据 </p>
     *
     * @param apiId     apiId
     */
    void removeByApiId(Long apiId);
}
