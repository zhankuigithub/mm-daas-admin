package com.mm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mm.system.domain.ApiCategoryPO;
import org.apache.ibatis.annotations.Param;

/**
 * api类目Mapper接口
 *
 * @author
 * @date 2023-04-03
 */
public interface ApiCategoryMapper extends BaseMapper<ApiCategoryPO> {

    /**
     * pan
     * @param name 名称
     * @param excludeId 排除id
     * @return
     */
    Long nameExist(@Param("name") String name, @Param("excludeId") Long excludeId);
}
