package com.mm.web.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mm.common.utils.BeanConverter;
import com.mm.system.domain.DatasourceInfoPO;
import com.mm.system.domain.command.DataSourceCommand;
import com.mm.system.domain.dto.*;
import com.mm.system.domain.query.DataSourceQuery;
import com.mm.system.service.IApiBaseRepository;
import com.mm.system.service.IDataSourceRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 数据源
 * </p>
 *
 * @author
 * @date 2023-03-25
 */
@Service
public class DataSourceDomainService {

    @Resource
    private IDataSourceRepository dataSourceRepository;
    @Resource
    private IApiBaseRepository apiBaseRepository;


    /**
     * 新增数据源
     *
     * @param command 新增数据源
     * @return boolean
     */
    public boolean saveDataSource(DataSourceCommand command) {
        return dataSourceRepository.save(BeanConverter.toBean(command, DatasourceInfoPO.class));
    }

    /**
     * 删除数据源
     *
     * @param id 数据源id
     * @return boolean
     */
    public boolean deleteDataSource(Long id) {
        return dataSourceRepository.removeById(id);
    }

    /**
     * 修改数据源
     *
     * @param command 修改数据源
     * @return boolean
     */
    public boolean modifyDataSource(DataSourceCommand command) {
        return dataSourceRepository.updateById(BeanConverter.toBean(command, DatasourceInfoPO.class));
    }

    /**
     * 分页查询
     *
     * @param query 查询参数
     * @return PageResultDTO
     */
    public PageResultDTO<DataSourceInfoDTO> pageQuery(DataSourceQuery query) {
        IPage<DatasourceInfoPO> page = dataSourceRepository.queryPage(query);
        PageInfoDTO<DataSourceInfoDTO> pageInfo = new PageInfoDTO<>(page.getTotal(), query.getCurPagerNo(), query.getPageSize(), BeanConverter.toList(page.getRecords(), DataSourceInfoDTO.class));
        return new PageResultDTO<>(pageInfo);
    }

    /**
     * 下拉列表
     *
     * @return List
     */
    public List<DataSourceInfoDTO> list() {
        List<DatasourceInfoPO> list = dataSourceRepository.
                list(new LambdaQueryWrapper<DatasourceInfoPO>()
                        .select(DatasourceInfoPO::getId,
                                DatasourceInfoPO::getDbIp,
                                DatasourceInfoPO::getDbPort,
                                DatasourceInfoPO::getDbName,
                                DatasourceInfoPO::getDataSourceName));

        return BeanConverter.toList(list, DataSourceInfoDTO.class);
    }

    public List<DataSourceTableDTO> tables(DataSourceCommand dataSourceCo) {
        return JdbcConnectionUtils.getTableList(dataSourceCo);
    }

    public List<DataSourceColumnDTO> tableColumns(DataSourceCommand dataSourceCo) {
        return JdbcConnectionUtils.getColumnByTableName(dataSourceCo);

    }

    public boolean testConnection(DataSourceCommand dataSourceCo) {
        return JdbcConnectionUtils.testConnection(dataSourceCo);
    }

    public DatasourceInfoPO info(Long id) {
        return BeanConverter.toBean(dataSourceRepository.getById(id), DatasourceInfoPO.class);
    }

    public boolean nameExist(String name, Long id) {
        return dataSourceRepository.nameExist(name, id);
    }
}
