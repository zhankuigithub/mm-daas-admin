package com.mm.web.application.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.mm.common.exception.ServiceException;
import com.mm.common.utils.RsaUtils;
import com.mm.system.domain.DatasourceInfoPO;
import com.mm.system.domain.command.DataSourceCommand;
import com.mm.system.domain.dto.DataSourceColumnDTO;
import com.mm.system.domain.dto.DataSourceInfoDTO;
import com.mm.system.domain.dto.DataSourceTableDTO;
import com.mm.system.domain.dto.PageResultDTO;
import com.mm.system.domain.query.ApiBasePageQuery;
import com.mm.system.domain.query.DataSourceQuery;
import com.mm.web.domain.service.ApiBaseDomainService;
import com.mm.web.domain.service.DataSourceDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@DS("slave")
public class DataSourceApplicationService {

    private final DataSourceDomainService dataSourceDomainService;
    private final ApiBaseDomainService apiBaseDomainService;

    /**
     * 新增数据源
     *
     * @param command 新增数据源
     * @return boolean
     */
    public boolean saveDataSource(DataSourceCommand command) {
        if (dataSourceDomainService.nameExist(command.getDataSourceName(), command.getId())) {
            throw new ServiceException("数据源名称已存在");
        }
        try {
            RsaUtils.decrypt(command.getDbPassword());
        } catch (Exception e) {
            throw new ServiceException("rsa异常");
        }
        return dataSourceDomainService.saveDataSource(command);
    }

    /**
     * 删除数据源
     *
     * @param id 数据源id
     * @return boolean
     */
    public boolean deleteDataSource(Long id) {
        //判断是否有接口使用
        if (apiBaseDomainService.hasApi(new ApiBasePageQuery().setDataSourceId(id))) {
            throw new ServiceException("该数据源下关联有接口信息，不可以删除~");
        }
        return dataSourceDomainService.deleteDataSource(id);
    }

    /**
     * 修改数据源
     *
     * @param command 修改数据源
     * @return boolean
     */
    public boolean modifyDataSource(DataSourceCommand command) {
        if (dataSourceDomainService.nameExist(command.getDataSourceName(), command.getId())) {
            throw new ServiceException("数据源名称已存在");
        }
        try {
            RsaUtils.decrypt(command.getDbPassword());
        } catch (Exception e) {
            throw new ServiceException("rsa异常");
        }
        return dataSourceDomainService.modifyDataSource(command);
    }

    /**
     * 分页查询
     *
     * @param query 查询参数
     * @return PageResultDTO
     */
    public PageResultDTO<DataSourceInfoDTO> pageQuery(DataSourceQuery query) {
        return dataSourceDomainService.pageQuery(query);
    }

    /**
     * 下拉列表
     *
     * @return List
     */
    public List<DataSourceInfoDTO> list() {
        return dataSourceDomainService.list();
    }

    public List<DataSourceTableDTO> tables(DataSourceCommand dataSourceCo) {
        return dataSourceDomainService.tables(dataSourceCo);
    }

    public List<DataSourceColumnDTO> tableColumns(DataSourceCommand dataSourceCo) {
        return dataSourceDomainService.tableColumns(dataSourceCo);
    }

    public boolean testConnection(DataSourceCommand dataSourceCo) {
        return dataSourceDomainService.testConnection(dataSourceCo);
    }

    public DatasourceInfoPO info(Long id) {
        DatasourceInfoPO info = dataSourceDomainService.info(id);
        if (info == null) {
            throw new ServiceException("数据不存在，请检查id");
        }
        return info;
    }
}
