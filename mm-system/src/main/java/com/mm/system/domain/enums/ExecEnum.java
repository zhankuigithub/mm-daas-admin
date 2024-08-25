package com.mm.system.domain.enums;

import lombok.Getter;


public enum ExecEnum {

    CREATE("C"), READ("R"), UPDATE("U"), DELETE("D");

    @Getter
    private String type;

    ExecEnum(String type) {
        this.type = type;
    }

}
