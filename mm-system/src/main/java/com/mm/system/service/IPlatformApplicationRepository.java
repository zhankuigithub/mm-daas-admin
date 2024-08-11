package com.mm.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mm.system.domain.PlatformApplicationPO;
import com.mm.system.domain.query.PlatformApplicationPageQuery;

/**
 * 平台应用Service接口
 *
 * @author
 * @date 2023-04-03
 */
public interface IPlatformApplicationRepository extends IService<PlatformApplicationPO> {
    IPage<PlatformApplicationPO> queryPage(PlatformApplicationPageQuery query);

    boolean nameExist(String name, Long excludeId);
}
