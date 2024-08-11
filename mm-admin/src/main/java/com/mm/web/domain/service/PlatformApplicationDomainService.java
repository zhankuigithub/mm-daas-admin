package com.mm.web.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mm.common.utils.BeanConverter;
import com.mm.system.domain.PlatformApplicationPO;
import com.mm.system.domain.command.PlatformApplicationCommand;
import com.mm.system.domain.dto.PageInfoDTO;
import com.mm.system.domain.dto.PlatformApplicationDTO;
import com.mm.system.domain.query.PlatformApplicationPageQuery;
import com.mm.system.service.IPlatformApplicationRepository;
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
public class PlatformApplicationDomainService {

    private final IPlatformApplicationRepository platformApplicationRepository;

    public boolean edit(PlatformApplicationCommand platformApplicationCommand) {
        return platformApplicationRepository.updateById(BeanConverter.toBean(platformApplicationCommand, PlatformApplicationPO.class));
    }

    public boolean save(PlatformApplicationCommand platformApplicationCommand) {
        return platformApplicationRepository.save(BeanConverter.toBean(platformApplicationCommand, PlatformApplicationPO.class));

    }

    public List<PlatformApplicationDTO> list() {
        return BeanConverter.toList(platformApplicationRepository.list(), PlatformApplicationDTO.class);
    }

    public boolean remove(Long id) {
        return platformApplicationRepository.removeById(id);
    }

    public PageInfoDTO<PlatformApplicationDTO> query(PlatformApplicationPageQuery query) {
        IPage<PlatformApplicationPO> page = platformApplicationRepository.queryPage(query);
        return new PageInfoDTO<>(page.getTotal(), query.getCurPagerNo(), query.getPageSize(), page.getRecords());
    }

    public boolean nameExist(String name, Long excludeId) {
        return platformApplicationRepository.nameExist(name, excludeId);
    }

    public PlatformApplicationPO getByAccount(String account) {
        return platformApplicationRepository.getOne(new LambdaQueryWrapper<PlatformApplicationPO>().eq(PlatformApplicationPO::getAppAccount, account));
    }

}

