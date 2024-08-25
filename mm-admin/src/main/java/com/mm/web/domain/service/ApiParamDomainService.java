package com.mm.web.domain.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mm.common.utils.BeanConverter;
import com.mm.system.domain.ApiParamPO;
import com.mm.system.domain.command.ApiParamCommand;
import com.mm.system.domain.dto.ApiParamDTO;
import com.mm.system.service.IApiParamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ApiParamDomainService {

    private final IApiParamRepository apiParamRepository;

    /**
     * <p> 批量保存api参数 </p>
     *
     * @param apiParams api参数
     * @param apiId     apiId
     * @return boolean
     */
    public boolean saveBatch(List<ApiParamCommand> apiParams, Long apiId) {
        if (CollectionUtils.isEmpty(apiParams)) {
            return false;
        }
        List<ApiParamPO> apiParamList = toApiParam(apiParams, apiId);
        return apiParamRepository.saveBatch(apiParamList);
    }

    private static List<ApiParamPO> toApiParam(List<ApiParamCommand> apiParams, Long apiId) {
        if (Objects.isNull(apiId)) {
            return BeanConverter.toList(apiParams, ApiParamPO.class);
        }
        return apiParams.stream().map(apiParamCommand -> {
                    apiParamCommand.setApiId(apiId);
                    return BeanConverter.toBean(apiParamCommand, ApiParamPO.class);
                }
        ).collect(Collectors.toList());
    }

    public void removeByApiId(Long id) {
        apiParamRepository.removeByApiId(id);
    }

    public List<ApiParamDTO> listByApiId(Long id) {
        return BeanConverter.toList(
                apiParamRepository.list(Wrappers.<ApiParamPO>lambdaQuery()
                        .eq(ApiParamPO::getApiId, id))
                , ApiParamDTO.class);
    }
}
