package com.mm.web.application.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.mm.web.domain.service.PlatformApplicationDomainService;
import com.mm.web.domain.service.PlatformServiceDomainService;
import com.mm.web.domain.service.ServiceAuthorizationDomainService;
import com.mm.common.exception.ServiceException;
import com.mm.system.domain.command.ApiBaseCommand;
import com.mm.system.domain.dto.ApiBaseDTO;
import com.mm.system.domain.dto.PageResultDTO;
import com.mm.system.domain.query.ApiBasePageQuery;
import com.mm.system.domain.query.PlatformServicePageQuery;
import com.mm.web.domain.service.ApiBaseDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author:
 * Date: 2023/3/30 16:28
 * Desc:
 */
@Component
@RequiredArgsConstructor
@DS("slave")
public class ApiBaseApplicationService {

    /** 接口 */
    private final ApiBaseDomainService apiBaseDomainService;

    /** 平台服务 */
    private final PlatformServiceDomainService platformServiceDomainService;

    /** 平台应用 */
    private final PlatformApplicationDomainService platformApplicationDomainService;

    /** 服务授权 */
    private final ServiceAuthorizationDomainService serviceAuthorizationDomainService;

    /**
     * <p> 外部查询 </p>
     *
     * @param apiName 接口名称
     * @param params  动态参数
     * @return Object
     **/
    public Object outerExecute(String apiName, Map<String, Object> params) {
        return apiBaseDomainService.outerExecute(apiName, params);
    }

    /**
     * <p> 新增接口 </p>
     *
     * @param command 新增接口实体
     * @return boolean
     **/
    public boolean saveApi(ApiBaseCommand command) {
        return apiBaseDomainService.saveOrUpdate(command);
    }

    /**
     * <p> 删除接口 </p>
     *
     * @param id 接口id
     * @return boolean
     **/
    public boolean deleteApi(Long id) {
        PlatformServicePageQuery query = new PlatformServicePageQuery();
        query.setApiIds(Collections.singletonList(id));
        if (platformServiceDomainService.hasService(query)) {
            throw new ServiceException("该接口关联有服务信息，不可以删除~");
        }
        return apiBaseDomainService.deleteApi(id);
    }

    /**
     * <p> 修改接口 </p>
     *
     * @param command 修改接口实体
     * @return boolean
     **/
    public boolean modifyApi(ApiBaseCommand command) {
        return apiBaseDomainService.saveOrUpdate(command);
    }

    /**
     * <p> 分页查询 </p>
     *
     * @param query 查询参数
     * @return PageResultDTO
     **/
    public PageResultDTO<ApiBaseDTO> pageQuery(ApiBasePageQuery query) {
        return apiBaseDomainService.pageQuery(query);
    }

    /**
     * <p> 动态接口列表 </p>
     *
     * @param query 接口查询实体
     * @return List
     **/
    public List<ApiBaseDTO> listQuery(ApiBasePageQuery query) {
        return apiBaseDomainService.listQuery(query);
    }


    public ApiBaseDTO info(Long id) {
        return apiBaseDomainService.info(id);
    }

    /**
     * <p> 执行sql </p>
     *
     * @param apiPath 路径
     * @param params  java.lang.Object
     * @return java.lang.Object
     **/
    public Object executeByPath(String apiPath, Map<String, Object> params) {
        return apiBaseDomainService.executeByPath(apiPath, Optional.ofNullable(params).orElse(new HashMap<>()));
    }


}

