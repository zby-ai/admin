package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.entity.Admin;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.Model;


/**
 * @author zbystart
 * @create 2021-02-06 12:10
 */
public interface AdminService {
    void saveAdmin(Admin admin);
    Admin getAdminById(Integer id);

    Admin getAdminByLoginAcctAndUserPswd(String loginAcct, String userPswd);

    PageInfo<Admin> getAdminListByPage(Integer pageNum, Integer pageSize,Integer pageFootCount);

    PageInfo<Admin> getAdminListByCondition(String queryCondition, Integer pageNum, Integer pageSize,Integer pageFootCount,Model model);

    void putAdminById(Admin admin);

    void removeAdminById(Integer id);

    void removeBatchAdminById(String[] adminIds);

    Admin getAdminByUsername(String username);
}
