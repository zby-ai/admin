package com.atguigu.atcrowdfunding.exception;

/**
 * @author zbystart
 * @create 2021-02-07 12:16
 */
public class CrowdAdminLoginException extends RuntimeException {
    public static final long serialVersionUID = -7034897190745766L;

    public CrowdAdminLoginException() {
    }

    public CrowdAdminLoginException(String message) {
        super(message);
    }
}
