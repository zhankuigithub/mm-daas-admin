package com.mm.web.application.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.mm.common.exception.ServiceException;
import com.mm.system.domain.command.ApiCategoryCommand;
import com.mm.system.domain.dto.ApiCategoryDTO;
import com.mm.system.domain.dto.PageResultDTO;
import com.mm.system.domain.query.ApiBasePageQuery;
import com.mm.system.domain.query.ApiCategoryPageQuery;
import com.mm.web.domain.service.ApiBaseDomainService;
import com.mm.web.domain.service.ApiCategoryDomainService;
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
public class ApiCategoryApplicationService {

    private final ApiCategoryDomainService apiCategoryDomainService;
    private final ApiBaseDomainService apiBaseDomainService;

    public boolean edit(ApiCategoryCommand apiCategoryCommand) {
        if(apiCategoryDomainService.nameExist(apiCategoryCommand.getCategoryName(), apiCategoryCommand.getId())){
            throw new ServiceException("类目名称已存在");
        }
        return apiCategoryDomainService.edit(apiCategoryCommand);
    }

    public boolean save(ApiCategoryCommand apiCategoryCommand) {
        if(apiCategoryDomainService.nameExist(apiCategoryCommand.getCategoryName(),  apiCategoryCommand.getId())){
            throw new ServiceException("类目名称已存在");
        }
        return apiCategoryDomainService.save(apiCategoryCommand);
    }

    public List<ApiCategoryDTO> list() {
        return apiCategoryDomainService.list();
    }

    public boolean remove(Long id) {
        //判断是否有接口使用
        if (apiBaseDomainService.hasApi(new ApiBasePageQuery().setCategoryId(id))) {
            throw new ServiceException("该类目下关联有接口信息，不可以删除~");
        }
        return apiCategoryDomainService.remove(id);
    }

    public PageResultDTO<ApiCategoryDTO> query(ApiCategoryPageQuery query) {
        return new PageResultDTO<>(apiCategoryDomainService.query(query));
    }

    public ApiCategoryDTO info(Long id) {
        ApiCategoryDTO info = apiCategoryDomainService.info(id);
        if (info == null) {
            throw new ServiceException("数据不存在，请检查id");
        }
        return info;
    }
}

