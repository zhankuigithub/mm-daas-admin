package com.mm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mm.system.domain.ServiceAuthorizationPO;
import com.mm.system.domain.dto.ServiceAuthorizationDTO;
import com.mm.system.domain.query.ServiceAuthorizationPageQuery;
import org.apache.ibatis.annotations.Param;

/**
 * 服务授权Mapper接口
 *
 * @author
 * @date 2023-04-03
 */
public interface ServiceAuthorizationMapper extends BaseMapper<ServiceAuthorizationPO> {

    /**
     * <p> 分页查询 </p>
     *
     * @param query ServiceAuthorizationPageQuery
     * @return IPage
     **/
    IPage<ServiceAuthorizationDTO> pageQuery(Page<ServiceAuthorizationDTO> page, @Param("param") ServiceAuthorizationPageQuery query);
}
