package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.entity.Auth;
import com.atguigu.atcrowdfunding.entity.AuthExample;
import com.atguigu.atcrowdfunding.mapper.AuthMapper;
import com.atguigu.atcrowdfunding.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zbystart
 * @create 2021-02-23 18:31
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAuthList() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getRoleAuthByRoleId(Integer roleId) {
        return authMapper.selectRoleAuthByRoleId(roleId);
    }

    @Override
    public void removeRoleAuthByRoleId(Integer roleId) {
        authMapper.deleteRoleAuthByRoleId(roleId);
    }

    @Override
    public void saveRoleAuth(Integer roleId, List<Integer> authIdList) {
        authMapper.insertRoleAuth(roleId,authIdList);
    }

    @Override
    public List<String> getRoleAuthNamesByAdminId(Integer adminId) {
        List<String> strings = authMapper.selectRoleAuthNamesByAdminId(adminId);
        return strings;
    }
}
