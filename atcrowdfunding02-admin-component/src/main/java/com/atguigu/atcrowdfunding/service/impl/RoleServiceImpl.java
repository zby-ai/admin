package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.constant.CrowdAdminConstant;
import com.atguigu.atcrowdfunding.entity.Role;
import com.atguigu.atcrowdfunding.entity.RoleExample;
import com.atguigu.atcrowdfunding.exception.CrowdAdminLoginAcctRedoException;
import com.atguigu.atcrowdfunding.mapper.RoleMapper;
import com.atguigu.atcrowdfunding.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zbystart
 * @create 2021-02-09 15:15
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询数据
     * @param pageNum
     * @param pageSize
     * @param queryCondition
     * @return
     */
    @Override
    public PageInfo<Role> getRoleList(Integer pageNum, Integer pageSize, Integer pageFootCount,String queryCondition) {
        PageHelper.startPage(pageNum,pageSize);
        List<Role> roles = null;
        PageInfo<Role> pageInfo = null;
        // 带有条件的查询
        if (queryCondition != null) {
//            RoleExample roleExample = new RoleExample();
//            roleExample.createCriteria().andNameLike("%" + queryCondition + "%");
//            roles = roleMapper.selectByExample(roleExample);

            roles = roleMapper.selectRoleListAndOrderByCondition(queryCondition);
            if (roles == null || roles.size() == 0) {
                return null;
            }
        }else {
            // 不带条件的查询
//            roles = roleMapper.selectByExample(new RoleExample());
            roles = roleMapper.selectRoleListAndOrder();
        }

        pageInfo = new PageInfo<>(roles,pageFootCount);
        return pageInfo;
    }

    /**
     * 删除数据
     * @param roleId
     */
    @Override
    public void removeRoleById(Integer roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
    }

    /**
     * 更新数据
     * @param role
     */
    @Override
    public void putRoleById(Role role) {
        try {
            roleMapper.updateByPrimaryKey(role);

        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new CrowdAdminLoginAcctRedoException("职位名称重复！");
            }
        }
    }

    /**
     * 添加数据
     * @param role
     */
    @Override
    public void saveRole(Role role) {
        try {
            roleMapper.insert(role);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new CrowdAdminLoginAcctRedoException("职位名称重复！");
            }
        }
    }

    /**
     * 批量删除
     * @param roleIds
     */
    @Override
    public void batchRemoveRoleById(String[] roleIds) {
        RoleExample roleExample = new RoleExample();
        ArrayList<Integer> arrayList = new ArrayList();

        for (String roleId : roleIds) {
            arrayList.add(Integer.parseInt(roleId));
        }
        roleExample.createCriteria().andIdIn(arrayList);
        roleMapper.deleteByExample(roleExample);
    }
}
