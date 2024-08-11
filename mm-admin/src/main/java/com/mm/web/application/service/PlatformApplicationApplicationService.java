package com.mm.web.application.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.mm.web.domain.service.PlatformApplicationDomainService;
import com.mm.web.domain.service.ServiceAuthorizationDomainService;
import com.mm.common.exception.ServiceException;
import com.mm.system.domain.command.PlatformApplicationCommand;
import com.mm.system.domain.dto.PageResultDTO;
import com.mm.system.domain.dto.PlatformApplicationDTO;
import com.mm.system.domain.query.PlatformApplicationPageQuery;
import com.mm.system.domain.query.ServiceAuthorizationPageQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author:
 * Date: 2023/3/30 16:28
 * Desc:
 */
@Component
@RequiredArgsConstructor
@DS("slave")
public class PlatformApplicationApplicationService {

    private final PlatformApplicationDomainService platformApplicationDomainService;
    private final ServiceAuthorizationDomainService serviceAuthorizationDomainService;


    public boolean edit(PlatformApplicationCommand platformApplicationCommand) {
        if (platformApplicationDomainService.nameExist(platformApplicationCommand.getAppName(), platformApplicationCommand.getId())) {
            throw new ServiceException("应用名称已存在");
        }
        return platformApplicationDomainService.edit(platformApplicationCommand);
    }

    public boolean save(PlatformApplicationCommand platformApplicationCommand) {
        if (platformApplicationDomainService.nameExist(platformApplicationCommand.getAppName(), platformApplicationCommand.getId())) {
            throw new ServiceException("应用名称已存在");
        }
        return platformApplicationDomainService.save(platformApplicationCommand);
    }

    public List<PlatformApplicationDTO> list() {
        return platformApplicationDomainService.list();
    }

    public boolean remove(Long id) {
        if (serviceAuthorizationDomainService.hasAuthorization(new ServiceAuthorizationPageQuery().setAppId(id))) {
            throw new ServiceException("该应用下关联有授权信息，不可以删除~");
        }
        return platformApplicationDomainService.remove(id);
    }

    public PageResultDTO<PlatformApplicationDTO> query(PlatformApplicationPageQuery query) {
        return new PageResultDTO<>(platformApplicationDomainService.query(query));
    }
}

