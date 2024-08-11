package com.mm.system.domain.enums;

import lombok.Getter;

/**
 * <p>
 * rsa错误代码
 * </p>
 *
 * @author
 * @date 2023-03-29
 */
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
