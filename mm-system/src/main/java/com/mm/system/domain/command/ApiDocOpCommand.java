package com.mm.system.domain.command;

import com.mm.system.domain.enums.CheckGroupEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class ApiDocOpCommand {

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空", groups = {CheckGroupEnum.Modify.class})
    private Long id;
}
