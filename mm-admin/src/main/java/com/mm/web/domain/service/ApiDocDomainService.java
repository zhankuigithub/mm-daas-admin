package com.mm.web.domain.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mm.system.domain.ApiDocPO;
import com.mm.system.domain.command.ApiDocCommand;
import com.mm.system.domain.command.ApiDocOpCommand;
import com.mm.system.domain.dto.PageInfoDTO;
import com.mm.system.domain.dto.PageResultDTO;
import com.mm.system.domain.query.ApiDocQuery;
import com.mm.system.service.IApiDocRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * <p>
 * 接口文档
 * </p>
 *
 * @author
 * @date 2023-04-07
 */
@Service
public class ApiDocDomainService {

    @Resource
    IApiDocRepository apiDocRepository;

    /**
     * <p> 新增 </p>
     *
     * @param command ApiDocCommand
     * @return boolean
     **/
    public boolean saveData(ApiDocCommand command) {
        ApiDocPO apiDocPo = BeanUtil.copyProperties(command, ApiDocPO.class);
        return apiDocRepository.save(apiDocPo);
    }

    /**
     * <p> 删除 </p>
     *
     * @param command ApiDocOpCommand
     * @return boolean
     **/
    public boolean deleteData(ApiDocOpCommand command) {
        return apiDocRepository.removeById(command.getId());
    }

    /**
     * <p> 编辑 </p>
     *
     * @param command ApiDocCommand
     * @return boolean
     **/
    public boolean updateData(ApiDocCommand command) {
        ApiDocPO apiDocPo = BeanUtil.copyProperties(command, ApiDocPO.class);
        return apiDocRepository.updateById(apiDocPo);
    }

    /**
     * 分页查询
     *
     * @param query ApiDocQuery
     * @return PageResultDTO
     */
    public PageResultDTO<ApiDocPO> pageQuery(ApiDocQuery query) {
        Page<ApiDocPO> page = apiDocRepository.page(new Page<>(query.getCurPagerNo(), query.getPageSize()),
                Wrappers.<ApiDocPO>lambdaQuery().
                        eq(query.getInterfaceId() != null, ApiDocPO::getInterfaceId, query.getInterfaceId()));

        PageInfoDTO<ApiDocPO> pageInfo = new PageInfoDTO<>(page.getTotal(), query.getCurPagerNo(), query.getPageSize(), page.getRecords());
        return new PageResultDTO<>(pageInfo);
    }

}
