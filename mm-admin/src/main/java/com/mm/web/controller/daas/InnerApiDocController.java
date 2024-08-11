package com.mm.web.controller.daas;

import com.mm.common.core.assembler.ResponseHelper;
import com.mm.common.core.assembler.ResponseMessage;
import com.mm.system.domain.command.ApiDocCommand;
import com.mm.system.domain.command.ApiDocOpCommand;
import com.mm.system.domain.enums.CheckGroupEnum;
import com.mm.system.domain.query.ApiDocQuery;
import com.mm.web.domain.service.ApiDocDomainService;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p>
 * 接口文档
 * </p>
 *
 * @author
 * @date 2023-04-07
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/inner/doc")
public class InnerApiDocController {

    @Resource
    private ApiDocDomainService apiDocService;

    /**
     * <p> 新增 </p>
     *
     * @param command ApiDocCommand
     * @return ResponseMessage
     **/
    @PostMapping("create")
    @ApiModelProperty("增")
    public ResponseMessage create(@RequestBody @Validated ApiDocCommand command) {
        return ResponseHelper.success(apiDocService.saveData(command));
    }

    /**
     * <p> 删除报表 </p>
     *
     * @param command ApiDocOpCommand
     * @return ResponseMessage
     **/
    @DeleteMapping("delete")
    @ApiModelProperty("删")
    public ResponseMessage delete(@RequestBody @Validated(value = {CheckGroupEnum.Modify.class}) ApiDocOpCommand command) {
        return ResponseHelper.success(apiDocService.deleteData(command));
    }

    /**
     * <p> 修改 </p>
     *
     * @param command ApiDocCommand
     * @return ResponseMessage
     **/
    @PutMapping("modify")
    @ApiModelProperty("改")
    public ResponseMessage modify(@RequestBody @Validated(value = CheckGroupEnum.Modify.class) ApiDocCommand command) {
        return ResponseHelper.success(apiDocService.updateData(command));
    }

    /**
     * <p> 分页查询 </p>
     *
     * @param query ApiDocQuery
     * @return ResponseMessage
     **/
    @PostMapping("page")
    @ApiModelProperty("查")
    public ResponseMessage page(@RequestBody ApiDocQuery query) {
        return ResponseHelper.success(apiDocService.pageQuery(query));
    }

}
