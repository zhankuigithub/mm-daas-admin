package com.mm.web.controller.daas;


import com.mm.common.core.assembler.ResponseHelper;
import com.mm.common.core.assembler.ResponseMessage;
import com.mm.system.domain.dto.ApiBaseLogDTO;
import com.mm.system.domain.dto.PageResultDTO;
import com.mm.system.domain.query.ApiLogPageQuery;
import com.mm.web.domain.service.ApiBaseLogDomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Api(tags = "报表发布日志相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/inner/api/log")
public class ApiBaseLogController {

    @Resource
    ApiBaseLogDomainService apiBaseLogDomainService;

    /**
     * <p> 列表查询 </p>
     *
     * @param apiId String
     * @return ResponseMessage
     **/
    @GetMapping("list")
    @ApiModelProperty("列表")
    public ResponseMessage<List<ApiBaseLogDTO>> list(String apiId) {
        return ResponseHelper.success(apiBaseLogDomainService.list(Long.parseLong(apiId)));
    }

    /**
     * <p> 分页查询 </p>
     *
     * @param query ApiLogPageQuery
     * @return ResponseMessage
     **/
    @PostMapping("page")
    @ApiModelProperty("列表")
    public ResponseMessage<PageResultDTO<ApiBaseLogDTO>> list(@RequestBody ApiLogPageQuery query) {
        return ResponseHelper.success(apiBaseLogDomainService.pageQuery(query));
    }

}
