package com.atguigu.atcrowdfunding.mapper;

import com.atguigu.atcrowdfunding.entity.Role;
import com.atguigu.atcrowdfunding.entity.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectRoleListAndOrder();

    List<Role> selectRoleListAndOrderByCondition(String queryCondition);

    List<Role> selectAdminAuthorityRoleByIdIsYes(Integer adminId);

    List<Role> selectAdminAuthorityRoleByIdIsNo(Integer adminId);

    int deleteAdminRoleByAdminIdAndRoleId(@Param("adminId") Integer adminId, @Param("roleId")Integer roleId);

    int insertAdminRole(@Param("adminId") Integer adminId, @Param("roleIds") List<Integer> roleIds);
}