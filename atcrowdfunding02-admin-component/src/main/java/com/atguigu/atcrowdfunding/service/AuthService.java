package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.entity.Auth;

import java.util.List;

/**
 * @author zbystart
 * @create 2021-02-23 18:31
 */
public interface AuthService {
    List<Auth> getAuthList();

    List<Integer> getRoleAuthByRoleId(Integer roleId);

    void removeRoleAuthByRoleId(Integer roleId);

    void saveRoleAuth(Integer roleId, List<Integer> authIdList);

    List<String> getRoleAuthNamesByAdminId(Integer adminId);
}
