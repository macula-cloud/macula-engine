package org.macula.cloud.core.exception;

/**
 * @Auther: Aaron
 * @Date: 2018/12/22 13:50
 * @Description:
 */
public class MaculaCloudException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public MaculaCloudException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public MaculaCloudException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public MaculaCloudException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public MaculaCloudException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}