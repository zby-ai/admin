package com.atguigu.atcrowdfunding.test;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zbystart
 * @create 2021-03-01 16:53
 */
public class SendMessageTest {
    public static void main(String[] args) {
        String host = "https://zwp.market.alicloudapi.com";
        String path = "/sms/sendv2";
        String method = "GET";
        String appcode = "d12f4b8452d2480095e4eb27312717ff";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("content", "【儿童教务】您正在登录验证,验证码为111232131231 ,60s内有效,请尽快验证。");
        querys.put("mobile", "175195182");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse httpResponse = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(httpResponse.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(httpResponse.getEntity()));
            StatusLine statusLine = httpResponse.getStatusLine();

            int statusCode = statusLine.getStatusCode();
            String reasonPhrase = statusLine.getReasonPhrase();
          System.out.println("响应码：" + statusCode + ", " + reasonPhrase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
