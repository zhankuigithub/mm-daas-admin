package com.mm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mm.system.domain.ServiceApiRelationPO;
import com.mm.system.mapper.ServiceApiRelationMapper;
import com.mm.system.service.IServiceApiRelationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务接口关系表 服务实现类
 * </p>
 *
 * @author
 * @since 2023-04-07
 */
@Service
public class ServiceApiRelationRepositoryImpl extends ServiceImpl<ServiceApiRelationMapper, ServiceApiRelationPO> implements IServiceApiRelationRepository {

    @Override
    public void modifyBatch(List<Long> apiIds, Long serverId) {

        // 获取现有的绑定关系
        List<ServiceApiRelationPO> poList = this.list(new LambdaQueryWrapper<ServiceApiRelationPO>().eq(ServiceApiRelationPO::getServerId, serverId));
        List<Long> oldApiIds = poList.stream().map(ServiceApiRelationPO::getApiId).collect(Collectors.toList());

        // 对比差异
        List<Long> addList = apiIds.stream().filter(item -> !oldApiIds.contains(item)).collect(Collectors.toList());
        List<Long> delList = oldApiIds.stream().filter(item -> !apiIds.contains(item)).collect(Collectors.toList());

        if (!delList.isEmpty()) {
            this.remove(new LambdaQueryWrapper<ServiceApiRelationPO>().eq(ServiceApiRelationPO::getServerId, serverId).in(ServiceApiRelationPO::getApiId, delList));
        }

        if (!addList.isEmpty()) {
            List<ServiceApiRelationPO> list = new ArrayList<>();
            for (Long apiId : addList) {
                ServiceApiRelationPO po = new ServiceApiRelationPO();
                po.setServerId(serverId);
                po.setApiId(apiId);
                list.add(po);
            }
            this.saveBatch(list);
        }

    }

    @Override
    public List<Long> listApiIdByServerId(Long serverId) {
        return list(Wrappers.<ServiceApiRelationPO>lambdaQuery()
                .eq(ServiceApiRelationPO::getServerId, serverId))
                .stream()
                .map(ServiceApiRelationPO::getApiId)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceApiRelationPO> listByApiId(List<Long> apiIds) {
        return list(Wrappers.<ServiceApiRelationPO>lambdaQuery()
                .in(ServiceApiRelationPO::getApiId, apiIds));
    }
}
