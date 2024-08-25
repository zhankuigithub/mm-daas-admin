package com.mm.web.controller.daas;

import com.mm.common.core.assembler.ResponseHelper;
import com.mm.common.core.assembler.ResponseMessage;
import com.mm.system.domain.command.ApiBaseCommand;
import com.mm.system.domain.dto.ApiBaseDTO;
import com.mm.system.domain.dto.PageResultDTO;
import com.mm.system.domain.query.ApiBasePageQuery;
import com.mm.web.application.service.ApiBaseApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "api内部相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/inner/api")
public class ApiBaseController {

    private final ApiBaseApplicationService apiBaseApplicationService;


    /**
     * <p> 添加接口 </p>
     *
     * @param command 接口对象
     * @return ResponseMessage
     **/
    @PostMapping("create")
    @ApiModelProperty("增")
    public ResponseMessage<Boolean> create(@RequestBody @Validated ApiBaseCommand command) {
        return ResponseHelper.success(apiBaseApplicationService.saveApi(command));
    }

    /**
     * <p> 删除接口 </p>
     *
     * @param id 接口id
     * @return ResponseMessage
     **/
    @DeleteMapping("delete")
    @ApiModelProperty("删")
    public ResponseMessage<Boolean> delete(Long id) {
        return ResponseHelper.success(apiBaseApplicationService.deleteApi(id));
    }

    /**
     * <p> 修改接口 </p>
     *
     * @param command 接口对象
     * @return ResponseMessage
     **/
    @PutMapping("modify")
    @ApiModelProperty("改")
    public ResponseMessage<Boolean> modify(@RequestBody @Validated ApiBaseCommand command) {
        return ResponseHelper.success(apiBaseApplicationService.modifyApi(command));
    }

    /**
     * <p> 分页查询 </p>
     *
     * @param query 分页参数
     * @return ResponseMessage
     **/
    @PostMapping("page")
    @ApiModelProperty("查")
    public ResponseMessage<PageResultDTO<ApiBaseDTO>> page(@RequestBody ApiBasePageQuery query) {
        return ResponseHelper.success(apiBaseApplicationService.pageQuery(query));
    }

    /**
     * <p> 列表查询 </p>
     *
     * @param query ApiBasePageQuery
     * @return ResponseMessage
     **/
    @PostMapping("list")
    @ApiModelProperty("列表")
    public ResponseMessage<List<ApiBaseDTO>> list(@RequestBody ApiBasePageQuery query) {
        return ResponseHelper.success(apiBaseApplicationService.listQuery(query));
    }


    /**
     * 获取详情
     * @param id
     * @return
     */
    @GetMapping("info")
    @ApiModelProperty("获取详情")
    public ResponseMessage<ApiBaseDTO> info(Long id) {
        return ResponseHelper.success(apiBaseApplicationService.info(id));
    }
}
