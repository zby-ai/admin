package com.atguigu.atcrowdfunding.exception;

/**
 * @author zbystart
 * @create 2021-02-07 12:24
 */
public class CrowdMd5Exception extends RuntimeException {
    static final long serialVersionUID = -703489719074579L;

    public CrowdMd5Exception() {
    }

    public CrowdMd5Exception(String message) {
        super(message);
    }
}
