package com.mm.system.design;

import com.alibaba.druid.pool.DruidDataSource;
import com.mm.common.exception.ServiceException;
import com.mm.common.utils.RsaUtils;
import com.mm.system.domain.DatasourceInfoPO;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 数据源管理工具
 * </p>
 *
 * @author
 * @date 2023-03-25
 */
public final class JdbcDataSourcePool {

    private static final int RETRY_CNT = 3;
    private static final int CONNECTION_TIME_OUT = 5000;

    /** 数据量连接池单例对象 */
    public static final JdbcDataSourcePool INSTANCE = new JdbcDataSourcePool();

    /** 接池map */
    private Map<Long, DruidDataSource> sourceMap = new ConcurrentHashMap<>();

    private JdbcDataSourcePool() {
    }

    /**
     * <p> 获取数据源连接池 </p>
     *
     * @param dataSource 数据源实例
     * @return DruidDataSource
     **/
    public DruidDataSource get(DatasourceInfoPO dataSource) {
        if (dataSource == null) {
            throw new ServiceException("数据源对象为空");
        }
        if (sourceMap.get(dataSource.getId()) != null) {
            return sourceMap.get(dataSource.getId());
        }
        DruidDataSource druidConfigSource = new DruidDataSource();
        druidConfigSource.setDriverClassName(dataSource.getDbDriver());
        druidConfigSource.setUsername(dataSource.getDbUserName());

        // 密码解密
        if (StringUtils.isNotBlank(dataSource.getDbPassword())) {
            dataSource.setDbPassword(RsaUtils.decrypt(dataSource.getDbPassword()));
        }
        druidConfigSource.setPassword(dataSource.getDbPassword());

        druidConfigSource.setUrl(dataSource.getDbUrl());
        //初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
        druidConfigSource.setInitialSize(dataSource.getInitSize() == null ? 2 : dataSource.getInitSize());
        // 最小连接池数量
        druidConfigSource.setMinIdle(dataSource.getMinSize() == null ? 1 : dataSource.getMinSize());
        // 最大连接池数量
        druidConfigSource.setMaxActive(dataSource.getMaxSize() == null ? 5 : dataSource.getMaxSize());
        // 失败后重连的次数
        druidConfigSource.setConnectionErrorRetryAttempts(RETRY_CNT);
        // 请求失败之后中断
        druidConfigSource.setBreakAfterAcquireFailure(true);
        // 超时时间
        druidConfigSource.setConnectTimeout(CONNECTION_TIME_OUT);
        sourceMap.put(dataSource.getId(), druidConfigSource);
        return druidConfigSource;
    }

}
