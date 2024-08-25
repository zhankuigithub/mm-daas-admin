package com.mm.web.domain.service;


import com.mm.common.exception.ServiceException;
import com.mm.common.utils.RsaUtils;
import com.mm.system.domain.command.DataSourceCommand;
import com.mm.system.domain.dto.DataSourceColumnDTO;
import com.mm.system.domain.dto.DataSourceTableDTO;
import com.mm.system.domain.enums.DataSourceTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class JdbcConnectionUtils {

    private JdbcConnectionUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static Connection getConnection(DataSourceCommand command) {
        if (command.getType() == null) {
            throw new ServiceException("数据库类型不能为空");
        }
        if (StringUtils.isBlank(command.getDbUrl())) {
            String url = String.format("jdbc:%s://%s:%s/", DataSourceTypeEnum.valueOf(command.getType()).getProtocol(), command.getDbIp(), command.getDbPort());
            command.setDbUrl(url);
        }
        try {
            Class.forName(command.getDbDriver());
            return DriverManager.getConnection(command.getDbUrl(), command.getDbUserName(), RsaUtils.decrypt(command.getDbPassword()));
        } catch (Exception e) {
            log.error("获取数据库连接失败 {}", e.getMessage(), e);
            throw new ServiceException("获取数据库连接失败");
        }
    }

    public static boolean testConnection(DataSourceCommand command) {
        try (Connection connection = getConnection(command)) {
            return connection.isValid(100);
        } catch (Exception e) {
            log.error("testConnection error {}", e.getMessage(), e);
            return false;
        }
    }

    public static List<String> getDataBaseList(DataSourceCommand command) {
        List<String> dbList = new ArrayList<>();
        try (Connection connection = getConnection(command)) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getSchemas();
            while (rs.next()) {
                dbList.add(rs.getString("TABLE_SCHEM"));
            }
        } catch (Exception e) {
            log.error("getDataBaseList error {}", e.getMessage(), e);
        }
        return dbList;
    }

    public static List<DataSourceTableDTO> getTableList(DataSourceCommand command) {
        List<DataSourceTableDTO> tableNameList = new ArrayList<>();
        try (Connection connection = getConnection(command)) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, command.getDbName(), "%", null);
            while (rs.next()) {
                tableNameList.add(
                        new DataSourceTableDTO().setTableName(rs.getString("TABLE_NAME"))
                                .setDesc(rs.getString("REMARKS"))
                );
            }
        } catch (Exception e) {
            log.error("getTableList error {}", e.getMessage(), e);
        }
        return tableNameList;
    }

    public static List<DataSourceColumnDTO> getColumnByTableName(DataSourceCommand command) {
        List<DataSourceColumnDTO> tableNameList = new ArrayList<>();
        try (Connection connection = getConnection(command)) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getColumns(null, command.getDbName(), command.getTableName(), null);
            while (rs.next()) {
                tableNameList.add(new DataSourceColumnDTO()
                        .setColumnName(rs.getString("COLUMN_NAME"))
                        .setColumnType(rs.getString("TYPE_NAME"))
                        .setDesc(rs.getString("REMARKS")));
            }
        } catch (Exception e) {
            log.error("getColumnByTableName error {}", e.getMessage(), e);
        }
        return tableNameList;
    }
}
