package com.atguigu.atcrowdfunding;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zbystart
 * @create 2021-02-09 15:05
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationcontext.xml")
public class TestRoleMapper {
    @Autowired
    private RoleMapper roleMapper;
    @Test
    public void testInsert(){

        for (int i = 0; i < 1000;i++){
            roleMapper.insert(new Role(null,"Role" + i));
        }
    }
}
