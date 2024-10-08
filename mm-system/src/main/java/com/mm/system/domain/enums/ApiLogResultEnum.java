package com.mm.system.domain.enums;

public enum ApiLogResultEnum {

    SUCCESS(200, "成功"),

    COMMON_ERROR(500, "通用错误");

    private Integer code;
    private String msg;

    ApiLogResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
