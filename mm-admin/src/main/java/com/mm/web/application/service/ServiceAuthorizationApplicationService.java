package com.mm.web.application.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mm.web.domain.service.ServiceAuthorizationDomainService;
import com.mm.system.domain.command.ServiceAuthorizationCommand;
import com.mm.system.domain.dto.PageResultDTO;
import com.mm.system.domain.dto.ServiceAuthorizationDTO;
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
public class ServiceAuthorizationApplicationService {

    private final ServiceAuthorizationDomainService serviceAuthorizationDomainService;

    public boolean edit(ServiceAuthorizationCommand serviceAuthorizationCommand) {
        return serviceAuthorizationDomainService.edit(serviceAuthorizationCommand);
    }

    public boolean save(ServiceAuthorizationCommand serviceAuthorizationCommand) {
        return serviceAuthorizationDomainService.save(serviceAuthorizationCommand);
    }

    public List<ServiceAuthorizationDTO> list() {
        return serviceAuthorizationDomainService.list();
    }

    public boolean remove(Long id) {
        return serviceAuthorizationDomainService.remove(id);
    }

    public PageResultDTO<ServiceAuthorizationDTO> query(ServiceAuthorizationPageQuery query) {
        return new PageResultDTO<>(serviceAuthorizationDomainService.query(query));
    }

    public String getAuthorizationCode() {
        // 从登录信息中获取，这里临时测试固定一个值
        return serviceAuthorizationDomainService.getAuthorizationCodeByAppId("cxk25ctrl");
    }
}

