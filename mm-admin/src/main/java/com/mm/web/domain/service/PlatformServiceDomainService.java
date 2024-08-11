package com.mm.web.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mm.common.utils.BeanConverter;
import com.mm.system.domain.PlatformServicePO;
import com.mm.system.domain.ServiceApiRelationPO;
import com.mm.system.domain.command.PlatformServiceCommand;
import com.mm.system.domain.dto.PageInfoDTO;
import com.mm.system.domain.dto.PlatformServiceDTO;
import com.mm.system.domain.query.PlatformServicePageQuery;
import com.mm.system.service.IPlatformServiceRepository;
import com.mm.system.service.IServiceApiRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:
 * Date: 2023/3/30 16:28
 * Desc:
 */
@Component
@RequiredArgsConstructor
public class PlatformServiceDomainService {

    private final IPlatformServiceRepository platformServiceRepository;

    private final IServiceApiRelationRepository serviceApiRelationRepository;

    @Transactional(rollbackFor = Exception.class)
    public boolean edit(PlatformServiceCommand platformServiceCommand) {
        PlatformServicePO platformServicePo = BeanConverter.toBean(platformServiceCommand, PlatformServicePO.class);
        boolean result = platformServiceRepository.updateById(platformServicePo);
        if (result) {
            serviceApiRelationRepository.modifyBatch(platformServiceCommand.getApiIds(), platformServiceCommand.getId());
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(PlatformServiceCommand platformServiceCommand) {
        PlatformServicePO platformServicePo = BeanConverter.toBean(platformServiceCommand, PlatformServicePO.class);
        boolean result = platformServiceRepository.save(platformServicePo);
        if (result) {
            serviceApiRelationRepository.modifyBatch(platformServiceCommand.getApiIds(), platformServicePo.getId());
        }
        return result;
    }

    public List<PlatformServiceDTO> list() {
        return BeanConverter.toList(platformServiceRepository.list(), PlatformServiceDTO.class);
    }

    public boolean remove(Long id) {
        serviceApiRelationRepository.
                remove(Wrappers.<ServiceApiRelationPO>lambdaQuery().eq(ServiceApiRelationPO::getServerId, id));
        return platformServiceRepository.removeById(id);
    }

    public PageInfoDTO<PlatformServiceDTO> query(PlatformServicePageQuery query) {
        IPage<PlatformServicePO> page = platformServiceRepository.pageQuery(query);
        List<PlatformServicePO> records = page.getRecords();

        List<PlatformServiceDTO> result = new ArrayList<>();
        for (PlatformServicePO record : records) {
            PlatformServiceDTO dto = BeanConverter.toBean(record, PlatformServiceDTO.class);
            List<ServiceApiRelationPO> list = serviceApiRelationRepository.list(new LambdaQueryWrapper<ServiceApiRelationPO>().eq(ServiceApiRelationPO::getServerId, record.getId()));
            if (list != null && !list.isEmpty()) {
                dto.setApiIds(list.stream().map(ServiceApiRelationPO::getApiId).collect(Collectors.toList()));
            }
            result.add(dto);
        }
        return new PageInfoDTO<>(page.getTotal(), query.getCurPagerNo(), query.getPageSize(), result);
    }

    public boolean hasService(PlatformServicePageQuery query) {
        return serviceApiRelationRepository.getOne(Wrappers.<ServiceApiRelationPO>lambdaQuery()
                .in(ServiceApiRelationPO::getApiId, query.getApiIds())
                .last("limit 1")) != null;
    }
    public boolean nameExist(String name, Long excludeId) {
        return platformServiceRepository.nameExist(name, excludeId);
    }

    public PlatformServiceDTO info(Long id) {
        return BeanConverter.toBean(platformServiceRepository.getById(id), PlatformServiceDTO.class);
    }
}

