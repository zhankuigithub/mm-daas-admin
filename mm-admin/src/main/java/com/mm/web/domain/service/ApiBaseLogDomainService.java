package com.mm.web.domain.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mm.common.utils.BeanConverter;
import com.mm.system.domain.ApiBaseLogPO;
import com.mm.system.domain.dto.ApiBaseLogDTO;
import com.mm.system.domain.dto.PageInfoDTO;
import com.mm.system.domain.dto.PageResultDTO;
import com.mm.system.domain.query.ApiLogPageQuery;
import com.mm.system.service.IApiBaseLogRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@DS("slave")
public class ApiBaseLogDomainService {

    @Resource
    IApiBaseLogRepository apiBaseLogRepository;

    /**
     * <p> 获取发布日志 </p>
     *
     * @param apiId 接口id
     * @return List
     **/
    public List<ApiBaseLogDTO> list(Long apiId) {
        List<ApiBaseLogPO> list = apiBaseLogRepository.list(new LambdaQueryWrapper<ApiBaseLogPO>().eq(ApiBaseLogPO::getApiId, apiId).orderByDesc(ApiBaseLogPO::getCreateTime));
        return BeanConverter.toList(list, ApiBaseLogDTO.class);
    }

    /**
     * <p> 分页获取发布日志 </p>
     *
     * @param query ApiLogPageQuery
     * @return PageResultDTO
     **/
    public PageResultDTO<ApiBaseLogDTO> pageQuery(ApiLogPageQuery query) {
        Page<ApiBaseLogPO> page = apiBaseLogRepository.page(new Page<>(query.getCurPagerNo(), query.getPageSize()),
                Wrappers.<ApiBaseLogPO>lambdaQuery().
                        eq(query.getApiId() != null, ApiBaseLogPO::getApiId, query.getApiId()).orderByDesc(ApiBaseLogPO::getCreateTime));
        PageInfoDTO<ApiBaseLogDTO> pageInfo = new PageInfoDTO<>(page.getTotal(), query.getCurPagerNo(), query.getPageSize(), BeanConverter.toList(page.getRecords(), ApiBaseLogDTO.class));
        return new PageResultDTO<>(pageInfo);
    }
}
