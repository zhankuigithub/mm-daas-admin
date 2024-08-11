package com.mm.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mm.system.domain.ApiBasePO;
import com.mm.system.domain.query.ApiBasePageQuery;


/**
 * 接口信息Service接口
 *
 * @author
 * @date 2023-03-30
 */
public interface IApiBaseRepository extends IService<ApiBasePO> {
    /**
     * 判断当前api名称是否存在
     *
     * @param id 接口信息主键
     * @return 接口信息
     */
    boolean nameExist(Long id, String name);

    /**
     * <p> api路径是否存在 </p>
     *
     * @param id      id
     * @param apiPath api路径
     * @return boolean
     */
    boolean apiPathExist(Long id, String apiPath);

    Page<ApiBasePO> pageQuery(ApiBasePageQuery query);

}
