package com.mm.web.application.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.mm.web.domain.service.PlatformServiceDomainService;
import com.mm.web.domain.service.ServiceAuthorizationDomainService;
import com.mm.common.exception.ServiceException;
import com.mm.system.domain.command.PlatformServiceCommand;
import com.mm.system.domain.dto.PageResultDTO;
import com.mm.system.domain.dto.PlatformServiceDTO;
import com.mm.system.domain.query.PlatformServicePageQuery;
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
public class PlatformServiceApplicationService {

    private final PlatformServiceDomainService platformServiceDomainService;
    private final ServiceAuthorizationDomainService serviceAuthorizationDomainService;


    public boolean edit(PlatformServiceCommand platformServiceCommand) {
        if(platformServiceDomainService.nameExist(platformServiceCommand.getServiceName(), platformServiceCommand.getId())){
            throw new ServiceException("服务名称已存在");
        }
        return platformServiceDomainService.edit(platformServiceCommand);
    }

    public boolean save(PlatformServiceCommand platformServiceCommand) {
        if(platformServiceDomainService.nameExist(platformServiceCommand.getServiceName(),  platformServiceCommand.getId())){
            throw new ServiceException("服务名称已存在");
        }
        return platformServiceDomainService.save(platformServiceCommand);
    }

    public List<PlatformServiceDTO> list() {
        return platformServiceDomainService.list();
    }

    public boolean remove(Long id) {
        if (serviceAuthorizationDomainService.hasAuthorization(new ServiceAuthorizationPageQuery().setServiceId(id))) {
            throw new ServiceException("该服务下关联有授权信息，不可以删除~");
        }
        return platformServiceDomainService.remove(id);
    }

    public PageResultDTO<PlatformServiceDTO> query(PlatformServicePageQuery query) {
        return new PageResultDTO<>(platformServiceDomainService.query(query));
    }

    public PlatformServiceDTO info(Long id) {
        return platformServiceDomainService.info(id);
    }
}

