package com.atguigu.atcrowdfunding.utils;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.atguigu.atcrowdfunding.constant.CrowdAdminConstant;
import com.atguigu.atcrowdfunding.exception.CrowdMd5Exception;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zbystart
 * @create 2021-02-06 17:50
 */
public class CrowdUtils {


    /**
     * 给远程第三方短信接口发送请求把验证码发送到用户手机上
     * @param host		短信接口调用的URL地址
     * @param path		具体发送短信功能的地址
     * @param method	请求方式
     * @param phoneNum	接收短信的手机号
     * @param appCode	用来调用第三方短信API的AppCode
     * @return 返回调用结果是否成功
     * 	成功：返回验证码
     * 	失败：返回失败消息
     * 	状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
     */
    public static ResultSetEntity<String> sendCodeByShortMessage(

            String host,

            String path,

            String method,

            String phoneNum,

            String appCode) {

        Map<String, String> headers = new HashMap<String, String>();

        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);

        // 封装其他参数
        Map<String, String> querys = new HashMap<String, String>();

        // 生成验证码
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 4; i++) {
            int random = (int) (Math.random() * 10);
            builder.append(random);
        }

        String code = builder.toString();

        String content = CrowdAdminConstant.TEMPLATE_SHORT_MESSAGE_PREFIX + code + CrowdAdminConstant.TEMPLATE_SHORT_MESSAGE_SUFFIX;
        querys.put("content", content);

        // 收短信的手机号
        querys.put("mobile", phoneNum);
        // JDK 1.8示例代码请在这里下载： http://code.fegine.com/Tools.zip

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);

            StatusLine statusLine = response.getStatusLine();

            // 状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
            int statusCode = statusLine.getStatusCode();

            String reasonPhrase = statusLine.getReasonPhrase();

            if(statusCode == 200) {
                System.out.println(EntityUtils.toString(response.getEntity()));
                // 操作成功，把生成的验证码返回
                return ResultSetEntity.succeedYesData("验证码发送成功",code);
            }

            return ResultSetEntity.failureYesData(reasonPhrase);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultSetEntity.failureYesData(e.getMessage());
        }
    }




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
