package com.atguigu.atcrowdfunding;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author zbystart
 * @create 2021-02-07 12:41
 */
public class CrowdUtilsTest {
    @Test
    public void testMd5(){
//        String str = CrowdUtils.md5("123123");
//        System.out.println(str);
        System.out.println(Integer.MAX_VALUE);
    }

    @Test
    public void testBCryptPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String paswd = "123123";
        String encode = bCryptPasswordEncoder.encode(paswd);
        System.out.println(encode);
    }
}
