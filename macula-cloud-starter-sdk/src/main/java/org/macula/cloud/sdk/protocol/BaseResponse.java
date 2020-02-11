package org.macula.cloud.sdk.protocol;

public class BaseResponse {

    private Integer code;
    private String message;

    public static BaseResponse result(Integer code, String message) {
        return new BaseResponse(code, message);
    }


    public BaseResponse() {
    }

    public BaseResponse(Integer code) {
        this.code = code;
    }

    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
