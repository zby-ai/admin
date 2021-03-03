package com.atguigu.atcrowdfunding;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zbystart
 * @create 2021-02-05 22:11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationcontext.xml")
public class TestAdminMapper {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Test
    public void testMapper(){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);
        for (int i=0; i< 1000; i++){
            Admin admin = new Admin();
            admin.setLoginAcct("LoginAcct" + i);
            admin.setUserPswd("UserPswd" + i);
            admin.setUserName("UserName" + i);
            admin.setEmail("email" + i + "@168.com");
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(date);
            admin.setCreateTime(format);
            mapper.insert(admin);
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSaveAdmin(){
        Logger logger = LoggerFactory.getLogger(TestAdminMapper.class);
        logger.info("程序正在执行");
        adminService.saveAdmin(new Admin(null, "admintest", "admin", "admintest", "admin@qq.com", "2021-01-02 10:20:30"));
    }

    @Test
    public void testLogicDelete(){
        String[] adminIds = {"54","55","56","57"};
        adminMapper.batchLogicDeleteAdminById(adminIds);

    }
}
