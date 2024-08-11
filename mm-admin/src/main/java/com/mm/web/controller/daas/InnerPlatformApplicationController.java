package com.mm.web.controller.daas;


import com.mm.common.core.assembler.ResponseHelper;
import com.mm.common.core.assembler.ResponseMessage;
import com.mm.system.domain.command.PlatformApplicationCommand;
import com.mm.system.domain.enums.CheckGroupEnum;
import com.mm.system.domain.query.PlatformApplicationPageQuery;
import com.mm.web.application.service.PlatformApplicationApplicationService;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 平台应用Controller
 *
 * @author
 * @date 2023-04-03
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/inner/application")
public class InnerPlatformApplicationController {
    private final PlatformApplicationApplicationService platformApplicationService;


    @GetMapping("/list")
    @ApiModelProperty("列表接口")
    public ResponseMessage list() {
        return ResponseHelper.success(platformApplicationService.list());
    }

    /**
     * <p> 分页查询平台应用列表 </p>
     * @param query 分页查询平台应用列表
     * @return responseMessage
     **/
    @PostMapping("/page")
    @ApiModelProperty("分页列表接口")
    public ResponseMessage page(@RequestBody PlatformApplicationPageQuery query) {
        return ResponseHelper.success(platformApplicationService.query(query));
    }


    /**
     * <p>
     * 新增平台应用
     * </p>
     *
     * @param platformApplicationCommand 创建应用对象
     * @return ResponseMessage 返回结果
     */
    @PostMapping("/create")
    @ApiModelProperty("增")
    public ResponseMessage<Boolean> add(@RequestBody @Validated PlatformApplicationCommand platformApplicationCommand) {
        return ResponseHelper.success(platformApplicationService.save(platformApplicationCommand));
    }

    /**
     * 修改平台应用
     */
    @PutMapping("/modify")
    @ApiModelProperty("改")
    public ResponseMessage edit(@RequestBody @Validated(value = CheckGroupEnum.Modify.class) PlatformApplicationCommand platformApplicationCommand) {
        return ResponseHelper.success(platformApplicationService.edit(platformApplicationCommand));
    }

    /**
     * 删除平台应用
     */
    @DeleteMapping("/delete")
    @ApiModelProperty("删")
    public ResponseMessage remove(Long id) {
        return ResponseHelper.success(platformApplicationService.remove(id));
    }
}
