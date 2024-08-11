package com.mm.web.controller.daas;


import com.mm.common.core.assembler.ResponseHelper;
import com.mm.common.core.assembler.ResponseMessage;
import com.mm.system.domain.command.DataSourceCommand;
import com.mm.system.domain.enums.CheckGroupEnum;
import com.mm.system.domain.enums.DataSourceTypeEnum;
import com.mm.system.domain.query.DataSourceQuery;
import com.mm.web.application.service.DataSourceApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 数据源相关接口
 * </p>
 *
 * @author
 * @date 2023-03-25
 */
@Api(tags = "数据源相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/inner/datasource")
public class InnerDatasourceInfoController {


    private final DataSourceApplicationService dataSourceApplicationService;

    /**
     * <p> 添加数据源 </p>
     *
     * @param command 数据源对象
     * @return ResponseMessage
     **/
    @PostMapping("create")
    @ApiModelProperty("增")
    public ResponseMessage create(@RequestBody @Validated DataSourceCommand command) {
        return ResponseHelper.success(dataSourceApplicationService.saveDataSource(command));
    }

    /**
     * <p> 删除数据源 </p>
     *
     * @param id 数据源id
     * @return ResponseMessage
     **/
    @DeleteMapping("delete")
    @ApiModelProperty("删")
    public ResponseMessage delete(Long id) {
        return ResponseHelper.success(dataSourceApplicationService.deleteDataSource(id));
    }

    /**
     * <p> 修改数据源 </p>
     *
     * @param command 数据源对象
     * @return ResponseMessage
     **/
    @PutMapping("modify")
    @ApiModelProperty("改")
    public ResponseMessage modify(@RequestBody @Validated(value = CheckGroupEnum.Modify.class) DataSourceCommand command) {
        return ResponseHelper.success(dataSourceApplicationService.modifyDataSource(command));
    }

    /**
     * <p> 分页查询 </p>
     *
     * @param query 分页参数
     * @return ResponseMessage
     **/
    @PostMapping("page")
    @ApiModelProperty("查")
    public ResponseMessage page(@RequestBody DataSourceQuery query) {
        return ResponseHelper.success(dataSourceApplicationService.pageQuery(query));
    }

    /**
     * <p> 下拉列表 </p>
     *
     * @return ResponseMessage
     **/
    @GetMapping("list")
    @ApiModelProperty("下拉接口")
    public ResponseMessage list() {
        return ResponseHelper.success(dataSourceApplicationService.list());
    }


    /**
     * 测试链接
     */
    @PostMapping("test")
    @ApiModelProperty("测试链接")
    public ResponseMessage testConnection(@RequestBody DataSourceCommand dataSourceCom) {
        return ResponseHelper.success(dataSourceApplicationService.testConnection(dataSourceCom));
    }

    /**
     * 获取当前库下所有表
     */
    @PostMapping("tables")
    @ApiModelProperty("获取当前库下所有表")
    public ResponseMessage tables(@RequestBody DataSourceCommand dataSourceCom) {
        return ResponseHelper.success(dataSourceApplicationService.tables(dataSourceCom));
    }

    /**
     * 获取当前表的字段
     */
    /**
     * <p>  </p>
     *  [com.kcwl.common.command.DataSourceCommand] com.kcwl.admin.application.assembler.ResponseMessage
     * @return com.kcwl.admin.application.assembler.ResponseMessage
     **/
    @PostMapping("columns")
    @ApiModelProperty("获取当前表的字段")
    public ResponseMessage tableColumns(@RequestBody DataSourceCommand dataSourceCom) {
        return ResponseHelper.success(dataSourceApplicationService.tableColumns(dataSourceCom));
    }
    /**
     * 获取支持数据类型
     */
    @GetMapping("types")
    @ApiModelProperty("支持数据类型")
    public ResponseMessage baseTypeList() {
        return ResponseHelper.success(DataSourceTypeEnum.getDataTypes());
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
    @GetMapping("info")
    @ApiModelProperty("获取详情")
    public ResponseMessage info(Long id) {
        return ResponseHelper.success(dataSourceApplicationService.info(id));
    }

}
