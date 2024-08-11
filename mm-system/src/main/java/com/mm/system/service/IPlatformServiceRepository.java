package com.mm.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mm.system.domain.PlatformServicePO;
import com.mm.system.domain.query.PlatformServicePageQuery;

/**
 * 平台服务Service接口
 *
 * @author
 * @date 2023-04-03
 */
public interface IPlatformServiceRepository extends IService<PlatformServicePO> {
    boolean nameExist(String name, Long excludeId);

    IPage<PlatformServicePO> pageQuery(PlatformServicePageQuery query);
}
