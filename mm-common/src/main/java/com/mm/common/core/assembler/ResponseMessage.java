package com.mm.common.core.assembler;

/**
 * @author:
 * Date: 2023/4/6 11:51
 * Desc:
 */
public class ResponseMessage<T> {
    private Integer code;
    private String message;
    private T result;

    public ResponseMessage() {
    }

    public ResponseMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result == null ? null : result;
    }

    public boolean isSuccess() {
        return this.code != null && this.code == 200;
    }
}
