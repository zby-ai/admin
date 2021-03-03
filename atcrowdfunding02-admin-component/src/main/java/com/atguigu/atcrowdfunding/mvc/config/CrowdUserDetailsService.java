package com.atguigu.atcrowdfunding.mvc.config;

import com.atguigu.atcrowdfunding.entity.Admin;
import com.atguigu.atcrowdfunding.entity.Role;
import com.atguigu.atcrowdfunding.service.AdminService;
import com.atguigu.atcrowdfunding.service.AuthService;
import com.atguigu.atcrowdfunding.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zbystart
 * @create 2021-02-24 19:38
 */
@Component
public class CrowdUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 获取当前登录的用户信息
        Admin admin = adminService.getAdminByUsername(username);

        Integer adminId = admin.getId();

        // 获取当前登录的用户所拥有的角色
        List<Role> roleList = roleService.getAdminAuthorityRoleByIdIsYes(adminId);

        // 获取当前用户所用的角色 所对应的权限
        List<String> authNameList = authService.getRoleAuthNamesByAdminId(adminId);

        List<GrantedAuthority> list = new ArrayList<>();

        // 开始封装 角色名称
        for (Role role : roleList) {
            String roleName = "ROLE_" + role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            list.add(simpleGrantedAuthority);
        }

        // 开始封装 权限名称
        for (String authName : authNameList) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
            list.add(simpleGrantedAuthority);
        }

        // 把封装好的用户名称、权限名称、用户信息存放到user中
        SecurityAdmin securityAdmin = new SecurityAdmin(admin, list);

        return securityAdmin;
    }
}
