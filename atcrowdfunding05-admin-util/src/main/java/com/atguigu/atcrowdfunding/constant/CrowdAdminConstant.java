package com.atguigu.atcrowdfunding.constant;

/**
 * @author zbystart
 * @create 2021-02-07 11:21
 */
public class CrowdAdminConstant {
    public static final String EXCEPTION_KEY = "exception";

    public static final String ADMIN_LOGIN_FAILURE = "用户名或密码输入错误!";
    public static final String ADMIN_DATA_ERROR_MSSAGE = "用户名重复！";
    public static final String ADMIN_MD5_ERROR_MSSAGE = "字符串不合法！";
    public static final String ADMIN_USER_KEY = "loginAdmin";
    public static final String ADMIN_LOGIN_MASSAGE = "loginMassage";
    public static final String ADMIN_PAGE_MASSAGE = "pageMassage";
    public static final String ADMIN_ADMIN = "admin";
    public static final String ADMIN_QUERYCONDITION = "queryCondition";
    public static final String ADMIN_PAGEINFO = "pageInfo";

    public static final Integer DEFALUT_PAGESIZE = 5;
    public static Integer pageSize = DEFALUT_PAGESIZE;
    public static final Integer DEFALUT_PAGEFOOTCOUNT = 10;
    public static Integer pageFootCount = DEFALUT_PAGEFOOTCOUNT;

    public static final String TEMPLATE_SHORT_MESSAGE_PREFIX = "【儿童教务】您正在登录验证,验证码为：";
    public static final String TEMPLATE_SHORT_MESSAGE_SUFFIX = " ,60s内有效,请尽快验证。";
    public static final String TEMPLATE_CODE_PREFIX = "CODE_KEY";
}
