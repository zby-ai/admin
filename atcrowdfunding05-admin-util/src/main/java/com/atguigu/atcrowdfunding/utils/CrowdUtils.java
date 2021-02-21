package com.atguigu.atcrowdfunding.utils;

import com.atguigu.atcrowdfunding.constant.CrowdAdminConstant;
import com.atguigu.atcrowdfunding.exception.CrowdMd5Exception;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zbystart
 * @create 2021-02-06 17:50
 */
public class CrowdUtils {
    /**
     * 使用MD5对字符串加密
     * @param source
     * @return 加密后的字符串
     */
    public static String md5(String source){
        if (source == null || source.length() == 0 ){
            throw new CrowdMd5Exception(CrowdAdminConstant.ADMIN_MD5_ERROR_MSSAGE);
        }
        String algorithm = "md5";
        MessageDigest instance = null;
        try {
            instance = MessageDigest.getInstance(algorithm);
            byte[] input = source.getBytes();
            byte[] output = instance.digest(input);
            //将加密好的字节数组以正数的方式封装到bigInteger对象中
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum,output);
            //把bigInteger中封装的数据以16进制的方式转换成字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断请求是否是json请求
     * @param request
     * @return json请求返回true,普通请求返回false
     */
    public static boolean judgeRequestType(HttpServletRequest request){
        String accept = request.getHeader("Accept");
        String requestedWith = request.getHeader("X-Requested-With");

        return (accept != null && accept.contains("application/json"))
                ||
                (requestedWith != null && requestedWith.contains("XMLHttpRequest"));
    }
}
