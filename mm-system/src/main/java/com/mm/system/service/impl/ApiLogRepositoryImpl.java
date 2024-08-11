package com.mm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mm.system.domain.ApiLogPO;
import com.mm.system.mapper.CustomApiLogMapper;
import com.mm.system.service.IApiLogRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2023-04-27
 */
@Service
public class ApiLogRepositoryImpl extends ServiceImpl<CustomApiLogMapper, ApiLogPO> implements IApiLogRepository {

    /** 限制长度 */
    private static final int MAX_LENGTH = 200;

    @Async
    @Override
    public void saveLog(String apiPath, String appId, Map<String, Object> params, int result, String ipAddress) {
        ApiLogPO apiLogPo = new ApiLogPO();
        apiLogPo.setApiPath(apiPath);
        if (params != null) {
            apiLogPo.setReqParam(truncateString(params.toString(), MAX_LENGTH));
        }
        apiLogPo.setAppId(0L);
        apiLogPo.setAppAccount(appId);
        apiLogPo.setResult(result);
        apiLogPo.setIpAddress(ipAddress);
        this.save(apiLogPo);
    }

    /**
     * <p> 机器去多余字符 </p>
     *
     * @param input
     * @param maxLength java.lang.String
     * @return java.lang.String
     **/
    public String truncateString(String input, int maxLength) {
        if (input == null || input.length() <= maxLength) {
            return input;
        } else {
            return input.substring(0, maxLength);
        }
    }
}
