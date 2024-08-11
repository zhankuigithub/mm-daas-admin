package com.mm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mm.system.domain.PlatformServicePO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 平台服务表 Mapper 接口
 * </p>
 *
 * @author
 * @since 2023-04-07
 */
public interface PlatformServiceMapper extends BaseMapper<PlatformServicePO> {

    Long nameExist(@Param("name") String name, @Param("excludeId") Long excludeId);
}
