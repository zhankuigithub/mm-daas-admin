package com.mm.system.domain.enums;

import lombok.Getter;

/**
 * <p>
 * 执行类型
 * </p>
 *
 * @author
 * @date 2023-03-25
 */
public enum ExecEnum {

    CREATE("C"), READ("R"), UPDATE("U"), DELETE("D");

    @Getter
    private String type;

    ExecEnum(String type) {
        this.type = type;
    }

}
