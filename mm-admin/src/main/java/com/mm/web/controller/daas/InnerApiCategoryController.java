package com.mm.web.controller.daas;


import com.mm.common.core.assembler.ResponseHelper;
import com.mm.common.core.assembler.ResponseMessage;
import com.mm.system.domain.command.ApiCategoryCommand;
import com.mm.system.domain.enums.CheckGroupEnum;
import com.mm.system.domain.query.ApiCategoryPageQuery;
import com.mm.web.application.service.ApiCategoryApplicationService;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/inner/category")
public class InnerApiCategoryController {
    private final ApiCategoryApplicationService apiCategoryApplicationService;

    /**
     * 查询api类目列表
     */
    @GetMapping("/list")
    @ApiModelProperty("列表接口")
    public ResponseMessage list() {
        return ResponseHelper.success(apiCategoryApplicationService.list());
    }
    /**
     * 分页查询api类目列表
     */
    @PostMapping("/page")
    @ApiModelProperty("分页列表接口")
    public ResponseMessage page(@RequestBody ApiCategoryPageQuery query) {
        return ResponseHelper.success(apiCategoryApplicationService.query(query));
    }


    /**
     * 新增api类目
     */
    @PostMapping("/create")
    @ApiModelProperty("增")
    public ResponseMessage add(@RequestBody @Validated ApiCategoryCommand apiCategoryCommand) {
        return ResponseHelper.success(apiCategoryApplicationService.save(apiCategoryCommand));
    }

    /**
     * 修改api类目
     */
    @PutMapping("/modify")
    @ApiModelProperty("改")
    public ResponseMessage edit(@RequestBody @Validated(value = CheckGroupEnum.Modify.class) ApiCategoryCommand apiCategoryCommand) {
        return ResponseHelper.success(apiCategoryApplicationService.edit(apiCategoryCommand));
    }

    /**
     * 删除api类目
     */
    @DeleteMapping("/delete")
    @ApiModelProperty("删")
    public ResponseMessage remove(Long id) {
        return ResponseHelper.success(apiCategoryApplicationService.remove(id));
    }


    /**
     * 获取详情
     */
    @GetMapping("info")
    @ApiModelProperty("获取详情")
    public ResponseMessage info(Long id) {
        return ResponseHelper.success(apiCategoryApplicationService.info(id));
    }
}
