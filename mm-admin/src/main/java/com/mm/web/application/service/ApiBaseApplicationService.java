package com.mm.web.application.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mm.common.utils.ExcelUtil;
import com.mm.system.domain.command.ApiBaseCommand;
import com.mm.system.domain.dto.ApiBaseDTO;
import com.mm.system.domain.dto.ApiParamDTO;
import com.mm.system.domain.dto.PageResultDTO;
import com.mm.system.domain.query.ApiBasePageQuery;
import com.mm.system.domain.query.PlatformServicePageQuery;
import com.mm.web.domain.service.ApiBaseDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@DS("slave")
public class ApiBaseApplicationService {

    private static final ObjectMapper sObjectMapper = new ObjectMapper();

    /**
     * 接口
     */
    private final ApiBaseDomainService apiBaseDomainService;


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

    public Object exportExcel(String apiPath, Map<String, Object> params) {
        Map<String, Object> map = Optional.ofNullable(params).orElse(new HashMap<>());
        map.remove("curPagerNo");
        map.remove("pageSize");
        Object o = apiBaseDomainService.executeByPath(apiPath, map);
        if (o instanceof List) {
            List list = (ArrayList) o;
            try {
                ApiBaseDTO info = apiBaseDomainService.info(apiPath);
                List<ApiParamDTO> apiParams = info.getApiReturns();
                List<ApiParamDTO> filteredList = apiParams.stream()
                        .filter(param -> param.getParamType() == 2)
                        .collect(Collectors.toList());
                List<String> remarks = filteredList.stream().map(ApiParamDTO::getRemark).collect(Collectors.toList());
                List<String> keys = filteredList.stream().map(ApiParamDTO::getFieldName).collect(Collectors.toList());
                ExcelUtil.downloadExcel(sObjectMapper.readTree(sObjectMapper.writeValueAsString(list)),
                        remarks,
                        keys, info.getApiName());
            } catch (IOException e) {
                log.error("export error:{}", e.getMessage());
            }
        }
        return null;
    }

}

