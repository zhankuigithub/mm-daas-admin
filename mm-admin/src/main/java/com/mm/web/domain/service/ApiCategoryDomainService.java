package com.mm.web.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mm.common.utils.BeanConverter;
import com.mm.system.domain.ApiCategoryPO;
import com.mm.system.domain.command.ApiCategoryCommand;
import com.mm.system.domain.dto.ApiCategoryDTO;
import com.mm.system.domain.dto.PageInfoDTO;
import com.mm.system.domain.query.ApiCategoryPageQuery;
import com.mm.system.service.IApiCategoryRepository;
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
public class ApiCategoryDomainService {

    private final IApiCategoryRepository apiCategoryRepository;

    public boolean edit(ApiCategoryCommand apiCategoryCommand) {
        return apiCategoryRepository.updateById(BeanConverter.toBean(apiCategoryCommand, ApiCategoryPO.class));
    }

    public boolean save(ApiCategoryCommand apiCategoryCommand) {
        return apiCategoryRepository.save(BeanConverter.toBean(apiCategoryCommand, ApiCategoryPO.class));

    }

    public List<ApiCategoryDTO> list() {
        return BeanConverter.toList(apiCategoryRepository.list(), ApiCategoryDTO.class);
    }

    public boolean remove(Long id) {
        return apiCategoryRepository.removeById(id);
    }

    public PageInfoDTO<ApiCategoryDTO> query(ApiCategoryPageQuery query) {
        IPage<ApiCategoryPO> page = apiCategoryRepository.queryPage(query);
        return new PageInfoDTO<>(page.getTotal(), query.getCurPagerNo(), query.getPageSize(), page.getRecords());
    }

    public boolean nameExist(String name, Long excludeId){
        return apiCategoryRepository.nameExist(name, excludeId);
    }

    public ApiCategoryDTO info(Long id) {
        return BeanConverter.toBean(apiCategoryRepository.getById(id),ApiCategoryDTO.class);
    }
}

