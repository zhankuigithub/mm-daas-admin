package com.mm.common.core.assembler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mm.common.enums.ResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author:
 * Date: 2023/4/6 11:44
 * Desc:
 */
@Slf4j
public class ResponseHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static final Object EMPTY_OBJECT = new HashMap<>();

    /**
     * 响应请求
     *
     * @param response
     * @param responseMessage
     * @throws Exception
     */
    public static void response(HttpServletResponse response, ResponseMessage responseMessage) throws IOException {
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseMessage));
    }


    /**
     * 返回正确结果
     *
     * @param
     * @return
     */
    public static ResponseMessage success() {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(ResponseCodeEnum.SUCCESS.getCode());
        responseMessage.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
        responseMessage.setResult("");
        return responseMessage;
    }


    /**
     * 返回正确结果
     *
     * @param result
     * @return
     */
    public static <T> ResponseMessage<T> success(T result) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(ResponseCodeEnum.SUCCESS.getCode());
        responseMessage.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
        responseMessage.setResult(result);
        return responseMessage;
    }

    /**
     * 返回正确结果
     *
     * @param result
     * @return
     */
    public static ResponseMessage success(String message, Object result) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(ResponseCodeEnum.SUCCESS.getCode());
        responseMessage.setMessage(message);
        responseMessage.setResult(result);
        return responseMessage;
    }

    /**
     * 返回正确结果
     *
     * @param message
     * @return
     */
    public static ResponseMessage successMessage(String message) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(ResponseCodeEnum.SUCCESS.getCode());
        responseMessage.setMessage(message);
        responseMessage.setResult(EMPTY_OBJECT);
        return responseMessage;
    }


    /**
     * 返回分页结果
     *
     * @param list
     * @return
     */
    public static ResponseMessage successList(List list) {
        return success(list);
    }

    /**
     * 根据指定错误码返回结果
     *
     * @param code
     * @param message
     * @return
     */
    public static ResponseMessage fail(Integer code, String message) {
        return createFailMessage(code, message);
    }

    public static ResponseMessage fail(String message) {
        return createFailMessage(ResponseCodeEnum.FAIL.getCode(), message);
    }

    public static ResponseMessage fail() {
        return createFailMessage(ResponseCodeEnum.FAIL.getCode(), ResponseCodeEnum.FAIL.getMessage());
    }

    public static ResponseMessage createFailMessage(Integer code, String message) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(code);
        responseMessage.setMessage(message);
        responseMessage.setResult(EMPTY_OBJECT);
        return responseMessage;
    }
}
