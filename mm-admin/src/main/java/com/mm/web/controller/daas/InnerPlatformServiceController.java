package com.mm.web.controller.daas;


import com.mm.common.core.assembler.ResponseHelper;
import com.mm.common.core.assembler.ResponseMessage;
import com.mm.system.domain.command.PlatformServiceCommand;
import com.mm.system.domain.dto.PageResultDTO;
import com.mm.system.domain.dto.PlatformServiceDTO;
import com.mm.system.domain.enums.CheckGroupEnum;
import com.mm.system.domain.query.PlatformServicePageQuery;
import com.mm.web.application.service.PlatformServiceApplicationService;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 平台服务Controller
 *
 * @author
 * @date 2023-04-03
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/inner/service")
public class InnerPlatformServiceController {
    private final PlatformServiceApplicationService platformServiceService;

    /**
     * 查询平台服务列表
     */
    @GetMapping("/list")
    @ApiModelProperty("列表接口")
    public ResponseMessage<List<PlatformServiceDTO>> list() {
        return ResponseHelper.success(platformServiceService.list());
    }

    /**
     * 分页查询平台服务列表
     */
    @PostMapping("/page")
    @ApiModelProperty("分页列表接口")
    public ResponseMessage<PageResultDTO<PlatformServiceDTO>> page(@RequestBody PlatformServicePageQuery query) {
        return ResponseHelper.success(platformServiceService.query(query));
    }


    /**
     * 新增平台服务
     */
    @PostMapping("/create")
    @ApiModelProperty("增")
    public ResponseMessage<Boolean> add(@RequestBody @Validated PlatformServiceCommand platformServiceCommand) {
        return ResponseHelper.success(platformServiceService.save(platformServiceCommand));
    }

    /**
     * 修改平台服务
     */
    @PutMapping("/modify")
    @ApiModelProperty("改")
    public ResponseMessage<Boolean> edit(@RequestBody @Validated(value = CheckGroupEnum.Modify.class) PlatformServiceCommand platformServiceCommand) {
        return ResponseHelper.success(platformServiceService.edit(platformServiceCommand));
    }

    /**
     * 删除平台服务
     */
    @DeleteMapping("/delete")
    @ApiModelProperty("删")
    public ResponseMessage<Boolean> remove(Long id) {
        return ResponseHelper.success(platformServiceService.remove(id));
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
    @GetMapping("info")
    @ApiModelProperty("获取详情")
    public ResponseMessage<PlatformServiceDTO> info(Long id) {
        return ResponseHelper.success(platformServiceService.info(id));
    }
}
