package com.atguigu.atcrowdfunding.exception;

/**
 * @author zbystart
 * @create 2021-02-08 11:37
 */
public class CrowdAdminLoginAcctRedoException extends RuntimeException {
    public static final long serialVersionUID = -7034897190745766939L;

    public CrowdAdminLoginAcctRedoException() {
    }

    public CrowdAdminLoginAcctRedoException(String message) {
        super(message);
    }
}
