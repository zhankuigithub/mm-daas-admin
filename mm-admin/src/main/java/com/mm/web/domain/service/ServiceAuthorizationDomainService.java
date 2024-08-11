package com.mm.web.domain.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mm.common.utils.BeanConverter;
import com.mm.system.domain.PlatformApplicationPO;
import com.mm.system.domain.ServiceAuthorizationPO;
import com.mm.system.domain.command.ServiceAuthorizationCommand;
import com.mm.system.domain.dto.PageInfoDTO;
import com.mm.system.domain.dto.ServiceAuthorizationDTO;
import com.mm.system.domain.query.ServiceAuthorizationPageQuery;
import com.mm.system.service.IPlatformApplicationRepository;
import com.mm.system.service.IServiceAuthorizationRepository;
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
public class ServiceAuthorizationDomainService {

    private final IServiceAuthorizationRepository serviceAuthorizationRepository;

    /** 平台应用 */
    private final IPlatformApplicationRepository platformApplicationRepository;

    public boolean edit(ServiceAuthorizationCommand serviceAuthorizationCommand) {
        return serviceAuthorizationRepository.updateById(BeanConverter.toBean(serviceAuthorizationCommand, ServiceAuthorizationPO.class));
    }

    public boolean save(ServiceAuthorizationCommand serviceAuthorizationCommand) {
        ServiceAuthorizationPO serviceAuthorizationPo = BeanConverter.toBean(serviceAuthorizationCommand, ServiceAuthorizationPO.class);
        serviceAuthorizationPo.setAuthorizationCode(DigestUtil.md5Hex(DigestUtil.md5Hex(String.valueOf(System.currentTimeMillis()))));
        return serviceAuthorizationRepository.save(serviceAuthorizationPo);

    }

    public List<ServiceAuthorizationDTO> list() {
        return BeanConverter.toList(serviceAuthorizationRepository.list(), ServiceAuthorizationDTO.class);
    }

    public boolean remove(Long id) {
        return serviceAuthorizationRepository.removeById(id);
    }

    public PageInfoDTO<ServiceAuthorizationDTO> query(ServiceAuthorizationPageQuery query) {
        IPage<ServiceAuthorizationDTO> page = serviceAuthorizationRepository.queryPage(query);
        return new PageInfoDTO<>(page.getTotal(), query.getCurPagerNo(), query.getPageSize(), page.getRecords());
    }

    public boolean hasAuthorization(ServiceAuthorizationPageQuery query) {
        query.setPageSize(1);
        return query(query).getRowsCount() > 0;
    }

    /**
     * <p> 获取一个授权信息 </p>
     *
     * @param appId 应用id（数据库的主机id）
     * @return ServiceAuthorizationPO
     **/
    public ServiceAuthorizationPO getByAppId(Long appId) {
        return serviceAuthorizationRepository.getOne(new LambdaQueryWrapper<ServiceAuthorizationPO>().eq(ServiceAuthorizationPO::getAppId, appId));
    }

    /**
     * <p> 获取授权码 </p>
     *
     * @param appAccount 应用账户（给出去时命名为appId）
     * @return java.lang.String
     **/
    public String getAuthorizationCodeByAppId(String appAccount) {
        PlatformApplicationPO platformApplicationPo = platformApplicationRepository.getOne(new LambdaQueryWrapper<PlatformApplicationPO>().eq(PlatformApplicationPO::getAppAccount, appAccount));
        if (platformApplicationPo != null) {
            ServiceAuthorizationPO serviceAuthorizationPo = getByAppId(platformApplicationPo.getId());
            if (serviceAuthorizationPo != null) {
                return serviceAuthorizationPo.getAuthorizationCode();
            }
            return null;
        }
        return null;
    }

}

