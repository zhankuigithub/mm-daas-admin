package com.mm.system.domain.enums;

import com.mm.common.utils.CommonUtils;
import com.mm.system.design.JdbcTemplatePool;
import com.mm.system.domain.ApiBasePO;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.wzy.sqltemplate.Configuration;
import org.wzy.sqltemplate.SqlMeta;
import org.wzy.sqltemplate.SqlTemplate;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * <p>
 * 执行枚举
 * </p>
 *
 * @author
 * @date 2023-03-31
 */
public enum ResultTypeExecEnum {

    /** 返回List类型 */
    DO_EXEC_LIST(ResultTypeEnum.LIST, (dynamicReportPo, params) ->
            dynamicReportPo.ifOpenHump() ?
                    CommonUtils.transformationHump(queryForList(dynamicReportPo, params)) : queryForList(dynamicReportPo, params)),

    /** 返回Map类型 */
    DO_EXEC_MAP(ResultTypeEnum.MAP, (dynamicReportPo, params) ->
            dynamicReportPo.ifOpenHump() ?
                    CommonUtils.transformationHump(queryForMap(dynamicReportPo, params)) : queryForMap(dynamicReportPo, params)),

    /** 返回Long类型 */
    DO_EXEC_LONG(ResultTypeEnum.LONG, (apiBasePersistent, params) -> queryForObject(apiBasePersistent, params, Long.class)),

    /** 返回Double类型 */
    DO_EXEC_DOUBLE(ResultTypeEnum.DOUBLE, (apiBasePersistent, params) -> queryForObject(apiBasePersistent, params, Double.class)),

    /** 返回String类型 */
    DO_EXEC_STRING(ResultTypeEnum.STRING, (apiBasePersistent, params) -> queryForObject(apiBasePersistent, params, String.class)),

    /** 返回Boolean类型 */
    DO_EXEC_BOOLEAN(ResultTypeEnum.BOOLEAN, (apiBasePersistent, params) -> queryForObject(apiBasePersistent, params, Boolean.class));

    @Getter
    private final ResultTypeEnum resultTypeEnum;

    @Getter
    private final BiFunction<ApiBasePO, Map<String, Object>, Object> consumer;


    ResultTypeExecEnum(ResultTypeEnum resultTypeEnum, BiFunction<ApiBasePO, Map<String, Object>, Object> consumer) {
        this.resultTypeEnum = resultTypeEnum;
        this.consumer = consumer;
    }

    /**
     * <p> 获取一个执行枚举 </p>
     *
     * @param type 返回值类型
     * @return DoExecEnum
     **/
    public static ResultTypeExecEnum findIn(String type) {
        try {
            return ResultTypeExecEnum.valueOf("DO_EXEC_"+type.toUpperCase());

        } catch (Exception e) {
            return DO_EXEC_LIST;
        }
    }

    /**
     * <p> 通过报表名称拉取对应的报表数据明细（map类型） </p>
     *
     * @param apiBasePersistent 动态报表实例
     * @param params          动态参数
     * @return Map<String, Object>
     **/
    public static Map<String, Object> queryForMap(ApiBasePO apiBasePersistent, Map<String, Object> params) {
        JdbcTemplate jdbcTemplate = JdbcTemplatePool.INSTANCE.get(apiBasePersistent.getDataSourceId());
        SqlMeta process = process(apiBasePersistent.getTemplateSql(), params);
        return jdbcTemplate.queryForMap(process.getSql(), process.getParameter().toArray());
    }

    /**
     * <p> 通过报表名称拉取对应的报表数据明细（list类型） </p>
     *
     * @param apiBasePersistent 动态报表实例
     * @param params          动态参数
     * @return List<Map < String, Object>>
     **/
    private static List<Map<String, Object>> queryForList(ApiBasePO apiBasePersistent, Map<String, Object> params) {
        JdbcTemplate jdbcTemplate = JdbcTemplatePool.INSTANCE.get(apiBasePersistent.getDataSourceId());
        SqlMeta process = process(apiBasePersistent.getTemplateSql(), params);
        return jdbcTemplate.queryForList(process.getSql(), process.getParameter().toArray());
    }

    /**
     * <p> 通过报表名称拉取对应的报表数据明细（数字类型） </p>
     *
     * @param apiBasePersistent 动态报表实例
     * @param params          动态参数
     * @param clazz           java.lang.Object
     * @return java.lang.Object
     **/
    public static Object queryForObject(ApiBasePO apiBasePersistent, Map<String, Object> params, Class<?> clazz) {
        JdbcTemplate jdbcTemplate = JdbcTemplatePool.INSTANCE.get(apiBasePersistent.getDataSourceId());
        SqlMeta process = process(apiBasePersistent.getTemplateSql(), params);
        return jdbcTemplate.queryForObject(process.getSql(), clazz, process.getParameter().toArray());
    }

    /**
     * <p> 构建SqlMeta </p>
     *
     * @param sql    sql模板
     * @param params 动态参数
     * @return 解析后的SqlMeta实例
     **/
    private static SqlMeta process(String sql, Map<String, Object> params) {
        Configuration configuration = new Configuration();
        SqlTemplate template = configuration.getTemplate(sql);
        return template.process(params);
    }
}
