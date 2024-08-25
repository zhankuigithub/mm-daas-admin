package com.mm.system.domain.enums;

import lombok.Getter;


@Getter
public enum RsaErrorCodeEnum {

    RSA_CODE_MSG("000000301", "Rsa校验错误");

    private String code;
    private String msg;

    RsaErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
