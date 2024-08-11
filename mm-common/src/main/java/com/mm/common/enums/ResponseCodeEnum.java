package com.mm.common.enums;

/**
 * @author:
 * Date: 2023/4/6 11:53
 * Desc:
 */
public enum ResponseCodeEnum {
    SUCCESS(200, "请求成功"),
    ILLEGAL_ARGS(202, "参数错误"),
    RSA_CODE_MSG(301, "Rsa校验错误"),
    AUTH_ILLEGAL(401, "用户认证不一致"),
    UN_AUTH(403, "授权异常，请重新退出登录或联系管理人员"),
    FAIL(500, "系统开小差请稍后再试");

    private Integer code;
    private String message;

    private ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
