package com.atguigu.atcrowdfunding.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * 可以放行的资源路径
 * @author zbystart
 * @create 2021-03-02 10:04
 */
public class AccessPassResources {

    public static final Set<String> PASS_RES_SET = new HashSet<>();
    static {
        PASS_RES_SET.add("/");
        PASS_RES_SET.add("/member/auth/to/login");
        PASS_RES_SET.add("/member/auth/to/reg/page");
        PASS_RES_SET.add("/member/auth/logout");
        PASS_RES_SET.add("/member/auth/get/code");
        PASS_RES_SET.add("/member/auth/check/loginacct");
        PASS_RES_SET.add("/member/auth/add/user");
    }

    public static final Set<String> STATIC_RES_SET = new HashSet<>();
    static {
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }
    /**
     *  用于判断某个 ServletPath 值是否对应一个静态资源
     * @param servletPath
     * @return
     * true：是静态资源
     * false：不是静态资源
     */
    public static boolean judgeCurrentServletPathWetherStaticResource(String servletPath) {
        // 1.排除字符串无效的情况
        if(servletPath == null || servletPath.length() == 0) {
            throw new RuntimeException("请求路径无效!");
        }
        // 2.根据“/”拆分 ServletPath 字符串
        String[] split = servletPath.split("/");
        // 3.考虑到第一个斜杠左边经过拆分后得到一个空字符串是数组的第一个元素，所以需 要使用下标 1 取第二个元素
        String firstLevelPath = split[1]; //
        // 4.判断是否在集合中
        return STATIC_RES_SET.contains(firstLevelPath);
    }
}
