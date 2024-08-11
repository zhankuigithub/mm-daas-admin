package com.mm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mm.system.domain.PlatformApplicationPO;
import org.apache.ibatis.annotations.Param;

/**
 * 平台应用Mapper接口
 *
 * @author
 * @date 2023-04-03
 */
public interface PlatformApplicationMapper extends BaseMapper<PlatformApplicationPO> {
    /**
     * <p> 检查名字是否存在 </p>
     *
     * @param name      应用名称
     * @param excludeId 主键id
     * @return java.lang.Long
     **/
    Long nameExist(@Param("name") String name, @Param("excludeId") Long excludeId);
}
