package com.mm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mm.system.domain.ApiLogPO;

import java.util.Map;

/**
 * <p>
 * 接口日志
 * </p>
 *
 * @author
 * @since 2023-04-27
 */
public interface IApiLogRepository extends IService<ApiLogPO> {

    /**
     * <p> 写日志 </p>
     *
     * @param apiPath
     * @param appId
     * @param params
     * @param result  void
     * @return void
     **/
    void saveLog(String apiPath, String appId, Map<String, Object> params, int result, String ipAddress);
}
