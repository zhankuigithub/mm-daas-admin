package com.mm.web.domain.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mm.common.exception.ServiceException;
import com.mm.common.utils.BeanConverter;
import com.mm.common.utils.CommonUtils;
import com.mm.system.design.JdbcTemplatePool;
import com.mm.system.domain.ApiBaseLogPO;
import com.mm.system.domain.ApiBasePO;
import com.mm.system.domain.command.ApiBaseCommand;
import com.mm.system.domain.command.ApiParamCommand;
import com.mm.system.domain.dto.ApiBaseDTO;
import com.mm.system.domain.dto.ApiParamDTO;
import com.mm.system.domain.dto.PageInfoDTO;
import com.mm.system.domain.dto.PageResultDTO;
import com.mm.system.domain.enums.ExecEnum;
import com.mm.system.domain.enums.ResultTypeExecEnum;
import com.mm.system.domain.query.ApiBasePageQuery;
import com.mm.system.service.IApiBaseLogRepository;
import com.mm.system.service.IApiBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.wzy.sqltemplate.Configuration;
import org.wzy.sqltemplate.SqlMeta;
import org.wzy.sqltemplate.SqlTemplate;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ApiBaseDomainService {

    /**
     * 页码
     */
    private static final String CUR_PAGE_NO = "curPagerNo";

    /**
     * 每页大小
     */
    private static final String PAGE_SIZE = "pageSize";

    /**
     * 接口
     */
    private final IApiBaseRepository apiBaseRepository;

    /**
     * 接口参数
     */
    private final ApiParamDomainService apiParamDomainService;

    /**
     * 动态报表操作日志
     */
    private final IApiBaseLogRepository apiBaseLogRepository;


    /**
     * <p> 查询接口 </p>
     *
     * @param apiPath 接口路径
     * @return 接口实例
     **/
    private ApiBasePO queryApiByPath(String apiPath) {
        ApiBasePO apiBasePersistent = apiBaseRepository.getOne(Wrappers.<ApiBasePO>lambdaQuery().eq(ApiBasePO::getApiPath, apiPath));
        if (apiBasePersistent == null) {
            throw new ServiceException("接口不存在");
        }
        return apiBasePersistent;
    }


    /**
     * <p> 执行查询 </p>
     *
     * @param apiBasePersistent 接口实例
     * @param params            动态参数
     * @return java.lang.Object
     **/
    public Object execute(ApiBasePO apiBasePersistent, Map<String, Object> params) {
        String execType = apiBasePersistent.getExecType();

        if (ExecEnum.READ.getType().equals(execType)) {
            if (params.containsKey(CUR_PAGE_NO) && params.containsKey(PAGE_SIZE)) {
                int pageNum = Integer.parseInt(params.get(CUR_PAGE_NO).toString());
                int pageSize = Integer.parseInt(params.get(PAGE_SIZE).toString());
                return page(apiBasePersistent, pageNum, pageSize, params);
            }

            return ResultTypeExecEnum.findIn(apiBasePersistent.getRespType()).getConsumer().apply(apiBasePersistent, params);

        } else if (ExecEnum.CREATE.getType().equals(execType)) {
            return insert(apiBasePersistent, params);
        } else {
            return update(apiBasePersistent, params);
        }
    }

    /**
     * <p> 通过接口名称拉取对应的接口数据明细 </p>
     *
     * @param apiBasePersistent 动态接口实例
     * @param params            动态参数
     * @return List<Map < String, Object>>
     **/
    public List<Map<String, Object>> list(ApiBasePO apiBasePersistent, Map<String, Object> params) {
        JdbcTemplate jdbcTemplate = JdbcTemplatePool.INSTANCE.get(apiBasePersistent.getDataSourceId());
        SqlMeta process = process(apiBasePersistent.getTemplateSql(), params);
        return jdbcTemplate.queryForList(process.getSql(), process.getParameter().toArray());
    }

    /**
     * <p> UD操作 </p>
     *
     * @param apiBasePersistent 动态接口实例
     * @param params            动态参数
     * @return int 影响行数
     **/
    public int update(ApiBasePO apiBasePersistent, Map<String, Object> params) {
        JdbcTemplate jdbcTemplate = JdbcTemplatePool.INSTANCE.get(apiBasePersistent.getDataSourceId());
        SqlMeta process = process(apiBasePersistent.getTemplateSql(), params);
        return jdbcTemplate.update(process.getSql(), process.getParameter().toArray());
    }

    /**
     * <p> C操作 </p>
     *
     * @param apiBasePersistent 动态接口实例
     * @param params            动态参数
     * @return Long 影响行数
     **/
    public Long insert(ApiBasePO apiBasePersistent, Map<String, Object> params) {
        JdbcTemplate jdbcTemplate = JdbcTemplatePool.INSTANCE.get(apiBasePersistent.getDataSourceId());
        SqlMeta process = process(apiBasePersistent.getTemplateSql(), params);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(process.getSql(), Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            for (Object p : process.getParameter().toArray()) {
                ps.setObject(i++, p);
            }
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    /**
     * <p> 通过接口名称拉取对应的接口数据明细 </p>
     *
     * @param apiBasePersistent 动态接口实例
     * @return PageInfoDTO
     **/
    public PageResultDTO<Object> page(ApiBasePO apiBasePersistent, Integer pageNum, Integer pageSize, Map<String, Object> params) {
        JdbcTemplate jdbcTemplate = JdbcTemplatePool.INSTANCE.get(apiBasePersistent.getDataSourceId());

        // 1. 构建count
        StringBuilder sqlCount = new StringBuilder();
        String payload = apiBasePersistent.getTemplateSql();
        sqlCount.append("select count(*) FROM(");
        sqlCount.append(payload);
        sqlCount.append(") as t");
        SqlMeta processCount = process(sqlCount.toString(), params);
        Integer count = jdbcTemplate.queryForObject(processCount.getSql(), Integer.class, processCount.getParameter().toArray());

        // 2.构建分页
        StringBuilder sqlPage = new StringBuilder();
        sqlPage.append(payload);
        pageNum = pageNum > 0 ? pageNum : 1;
        int offset = (pageNum - 1) * pageSize;

        params.put("offset", offset);
        params.put("size", pageSize);
        sqlPage.append(" limit #{offset}, #{size}");

        SqlMeta processPage = process(sqlPage.toString(), params);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(processPage.getSql(), processPage.getParameter().toArray());
        if (apiBasePersistent.ifOpenHump()) {
            maps = CommonUtils.transformationHump(maps);
        }
        PageInfoDTO<Object> pageInfo = new PageInfoDTO<>(count, pageNum, pageSize, maps);
        return new PageResultDTO<>(pageInfo);
    }

    /**
     * <p> 构建SqlMeta </p>
     *
     * @param sql    sql模板
     * @param params 动态参数
     * @return 解析后的SqlMeta实例
     **/
    public SqlMeta process(String sql, Map<String, Object> params) {
        Configuration configuration = new Configuration();
        SqlTemplate template = configuration.getTemplate(sql);
        return template.process(params);
    }


    /**
     * <p> 删除接口 </p>
     *
     * @param id 接口id
     * @return boolean
     **/
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteApi(Long id) {
        //移除参数
        apiParamDomainService.removeByApiId(id);
        return apiBaseRepository.removeById(id);
    }

    /**
     * <p> 修改接口 </p>
     *
     * @param command 修改接口实体
     * @return boolean
     **/
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(ApiBaseCommand command) {
        if (apiBaseRepository.nameExist(command.getId(), command.getApiName())) {
            throw new ServiceException("接口名称已存在~");
        }
        if (apiBaseRepository.apiPathExist(command.getId(), command.getApiName())) {
            throw new ServiceException("接口路径已存在~");
        }
        if (command.getId() != null) {
            //移除原有参数
            apiParamDomainService.removeByApiId(command.getId());
        }
        ApiBasePO apiBasePersistent = BeanConverter.toBean(command, ApiBasePO.class);

        // 是否记录发布日志
        boolean saveLogFlag = true;

        // update时获取旧的执行语句
        String oldSql = "";
        if (command.getId() != null) {
            ApiBasePO apiBasePo = apiBaseRepository.getOne(Wrappers.<ApiBasePO>lambdaQuery().eq(ApiBasePO::getId, command.getId()).select(ApiBasePO::getTemplateSql));
            oldSql = apiBasePo.getTemplateSql();
        }

        boolean saveOrUpdate = apiBaseRepository.saveOrUpdate(apiBasePersistent);
        if (!saveOrUpdate) {
            //新增参数
            throw new ServiceException("新增或更新接口信息时出现错误~");
        }

        // 只有当update && sql未发生变化时才不写
        if (command.getId() != null && command.getTemplateSql().equals(oldSql)) {
            saveLogFlag = false;
        }

        if (saveLogFlag) {
            ApiBaseLogPO apiBaseLogPo = new ApiBaseLogPO();
            apiBaseLogPo.setApiId(apiBasePersistent.getId());
            apiBaseLogPo.setRemark(command.getRemark());
            apiBaseLogPo.setTemplateSql(command.getTemplateSql());
            apiBaseLogRepository.save(apiBaseLogPo);
        }

        List<ApiParamCommand> paramList = new ArrayList<>();
        Optional.ofNullable(command.getApiParams()).ifPresent(paramList::addAll);
        Optional.ofNullable(command.getApiReturns()).ifPresent(paramList::addAll);
        if (CollectionUtils.isNotEmpty(paramList)) {
            //新增参数
            return apiParamDomainService.saveBatch(paramList, apiBasePersistent.getId());
        }
        return true;
    }

    /**
     * <p> 分页查询 </p>
     *
     * @param query 查询参数
     * @return PageResultDTO
     **/
    public PageResultDTO<ApiBaseDTO> pageQuery(ApiBasePageQuery query) {
        Page<ApiBasePO> page = apiBaseRepository.pageQuery(query);

        List<ApiBaseDTO> list = new ArrayList<>();

        for (ApiBasePO apiBase : page.getRecords()) {
            ApiBaseDTO apiBaseDto = BeanUtil.copyProperties(apiBase, ApiBaseDTO.class);
            list.add(apiBaseDto);
        }
        PageInfoDTO<ApiBaseDTO> pageInfo = new PageInfoDTO<>(page.getTotal(), query.getCurPagerNo(), query.getPageSize(), list);
        return new PageResultDTO<>(pageInfo);
    }

    public boolean hasApi(ApiBasePageQuery apiBasePageQuery) {
        //判断是否有接口使用
        apiBasePageQuery.setPageSize(1);
        PageResultDTO<ApiBaseDTO> apiBaseDTOPageResultDTO = pageQuery(apiBasePageQuery);
        return apiBaseDTOPageResultDTO.getPage().getRowsCount() > 0;
    }


    /**
     * <p> 动态接口列表 </p>
     *
     * @param query 接口查询实体
     * @return List
     **/
    public List<ApiBaseDTO> listQuery(ApiBasePageQuery query) {

        // 接口列表
        List<ApiBasePO> list = apiBaseRepository.list(new LambdaQueryWrapper<ApiBasePO>()
                .like(CharSequenceUtil.isNotBlank(query.getApiName()), ApiBasePO::getApiName, query.getApiName())
                .eq(query.getApiPath() != null, ApiBasePO::getApiPath, query.getApiPath())
                .eq(query.getCategoryId() != null, ApiBasePO::getCategoryId, query.getCategoryId())
                .in(CollectionUtils.isNotEmpty(query.getIds()), ApiBasePO::getId, query.getIds())
        );
        List<ApiBaseDTO> result = BeanConverter.toList(list, ApiBaseDTO.class);
        return result;
    }


    public ApiBaseDTO info(Long id) {
        ApiBaseDTO apiBaseDto = BeanConverter.toBean(apiBaseRepository.getById(id), ApiBaseDTO.class);

        //填充参数及返回值
        List<ApiParamDTO> apiParamList = apiParamDomainService.listByApiId(id);
        if (CollectionUtils.isNotEmpty(apiParamList)) {
            Map<Integer, List<ApiParamDTO>> collect = apiParamList.stream().collect(Collectors.groupingBy(ApiParamDTO::getParamType));
            apiBaseDto.setApiParams(collect.get(1));
            apiBaseDto.setApiReturns(collect.get(2));
        }
        return apiBaseDto;
    }

    public Object executeByPath(String apiPath, Map<String, Object> params) {
        return this.execute(queryApiByPath(apiPath), params);
    }


    public ApiBaseDTO info(String apiPath) {
        ApiBasePO apiBasePo = queryApiByPath(apiPath);
        return this.info(apiBasePo.getId());
    }

}

