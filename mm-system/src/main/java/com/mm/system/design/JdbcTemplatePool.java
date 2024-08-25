package com.mm.system.design;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.mm.common.exception.ServiceException;
import com.mm.system.domain.DatasourceInfoPO;
import com.mm.system.service.IDataSourceRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public final class JdbcTemplatePool {

    /**  静态成员变量 */
    public static final JdbcTemplatePool INSTANCE = new JdbcTemplatePool();

    /** JdbcTemplate模板map */
    private final Map<Long, JdbcTemplate> templateMap = new ConcurrentHashMap<>();

    private JdbcTemplatePool() {
    }

    /**
     * <p> 获取连接模板 </p>
     *
     * @param dsId 数据源id
     * @return org.springframework.jdbc.core.JdbcTemplate
     **/
    public JdbcTemplate get(Long dsId) {
        if (dsId == null) {
            throw new ServiceException("数据源或者名称为空");
        }
        if (templateMap.get(dsId) != null) {
            return templateMap.get(dsId);
        }

        // 首先查询数据源详细信息
        IDataSourceRepository dataSourceRepository = SpringUtil.getBean(IDataSourceRepository.class);
        DatasourceInfoPO dataSource = dataSourceRepository.getById(dsId);
        if (dataSource == null) {
            throw new ServiceException("数据源查询不到");
        }
        return getTemplateMap(dataSource);
    }


    /**
     * <p> 生成新的template </p>
     *
     * @param dataSource 数据源实例
     * @return org.springframework.jdbc.core.JdbcTemplate
     **/
    private JdbcTemplate getTemplateMap(DatasourceInfoPO dataSource) {
        synchronized (JdbcTemplatePool.class) {
            if (templateMap.get(dataSource.getId()) != null) {
                return templateMap.get(dataSource.getId());
            }

            // 生成连接池
            DruidDataSource druidDataSource = JdbcDataSourcePool.INSTANCE.get(dataSource);
            if (druidDataSource == null) {
                throw new ServiceException("获取数据库连接池异常");
            }

            // 生成连接模版
            JdbcTemplate jdbcTemplate = new JdbcTemplate(druidDataSource);
            templateMap.put(dataSource.getId(), jdbcTemplate);
            return jdbcTemplate;
        }
    }

}
