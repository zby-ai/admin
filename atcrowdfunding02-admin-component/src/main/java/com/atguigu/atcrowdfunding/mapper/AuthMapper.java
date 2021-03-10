package com.atguigu.atcrowdfunding.mapper;

import com.atguigu.atcrowdfunding.entity.Auth;
import com.atguigu.atcrowdfunding.entity.AuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthMapper {
    int countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    List<Integer> selectRoleAuthByRoleId(Integer roleId);

    void deleteRoleAuthByRoleId(Integer roleId);

    void insertRoleAuth(@Param("roleId") Integer roleId,@Param("authIdList") List<Integer> authIdList);


    List<String> selectRoleAuthNamesByAdminId(Integer adminId);
}