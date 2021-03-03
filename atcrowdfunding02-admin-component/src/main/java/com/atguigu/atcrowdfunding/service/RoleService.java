package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.entity.Role;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.Model;

import java.util.List;

/**
 * @author zbystart
 * @create 2021-02-09 15:14
 */
public interface RoleService {

    PageInfo<Role> getRoleList(Integer pageNum, Integer pageSize,Integer pageFootCount,String queryCondition);

    void removeRoleById(Integer roleId);

    void putRoleById(Role role);

    void saveRole(Role role);

    void batchRemoveRoleById(String[] roleIds);

    List<Role> getAdminAuthorityRoleByIdIsYes(Integer adminId);
    List<Role> getAdminAuthorityRoleByIdIsNo(Integer adminId);

    void removeAdminRoleByAdminIdAndRoleId(Integer adminId, List<Integer> roleIds);

    void saveAdminRole(Integer adminId, List<Integer> roleIds);
}
