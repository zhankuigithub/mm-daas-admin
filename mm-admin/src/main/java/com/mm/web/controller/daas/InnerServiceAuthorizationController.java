package com.mm.web.controller.daas;


import com.mm.common.core.assembler.ResponseHelper;
import com.mm.common.core.assembler.ResponseMessage;
import com.mm.system.domain.command.ServiceAuthorizationCommand;
import com.mm.system.domain.enums.CheckGroupEnum;
import com.mm.system.domain.query.ServiceAuthorizationPageQuery;
import com.mm.web.application.service.ServiceAuthorizationApplicationService;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 服务授权Controller
 *
 * @author
 * @date 2023-04-03
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/inner/authorization")
public class InnerServiceAuthorizationController {
    private final ServiceAuthorizationApplicationService serviceAuthorizationApplicationService;

    /**
     * 查询服务授权列表
     */
    @GetMapping("/list")
    @ApiModelProperty("列表接口")
    public ResponseMessage list() {
        return ResponseHelper.success(serviceAuthorizationApplicationService.list());
    }

    /**
     * 分页查询服务授权列表
     */
    @PostMapping("/page")
    @ApiModelProperty("分页列表接口")
    public ResponseMessage page(@RequestBody ServiceAuthorizationPageQuery query) {
        return ResponseHelper.success(serviceAuthorizationApplicationService.query(query));
    }


    /**
     * 新增服务授权
     */
    @PostMapping("/create")
    @ApiModelProperty("增")
    public ResponseMessage add(@RequestBody @Validated ServiceAuthorizationCommand serviceAuthorizationCommand) {
        return ResponseHelper.success(serviceAuthorizationApplicationService.save(serviceAuthorizationCommand));
    }

    /**
     * 修改服务授权
     */
    @PutMapping("/modify")
    @ApiModelProperty("改")
    public ResponseMessage edit(@RequestBody @Validated(value = CheckGroupEnum.Modify.class) ServiceAuthorizationCommand serviceAuthorizationCommand) {
        return ResponseHelper.success(serviceAuthorizationApplicationService.edit(serviceAuthorizationCommand));
    }

    /**
     * 删除服务授权
     */
    @DeleteMapping("/delete")
    @ApiModelProperty("删")
    public ResponseMessage remove(Long id) {
        return ResponseHelper.success(serviceAuthorizationApplicationService.remove(id));
    }

    @GetMapping("getAuthorizationCode")
    @ApiModelProperty("获取鉴权码")
    public ResponseMessage getAuthorizationCode() {
        return ResponseHelper.success(serviceAuthorizationApplicationService.getAuthorizationCode());
    }

}
