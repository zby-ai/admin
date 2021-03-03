package com.atguigu.atcrowdfunding.mvc.controller;

import com.atguigu.atcrowdfunding.entity.Auth;
import com.atguigu.atcrowdfunding.service.AuthService;
import com.atguigu.atcrowdfunding.utils.ResultSetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zbystart
 * @create 2021-02-23 18:29
 */
@RestController
@RequestMapping("/admin/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    /**
     * 获取权限列表
     * @return
     */
    @GetMapping("/get/list")
    public ResultSetEntity<List<Auth>> getList() {
        List<Auth> authList = authService.getAuthList();
        return ResultSetEntity.succeedYesData("查询成功！",authList);
    }

    /**
     * 获取角色的权限
     */
    @GetMapping("/get/roleAuth/{roleId}")
    public ResultSetEntity<List<Integer>> getRoleAuth(@PathVariable("roleId") Integer roleId) {
        List<Integer> authIdList = authService.getRoleAuthByRoleId(roleId);
        return ResultSetEntity.succeedYesData("查询成功！",authIdList);
    }

    /**
     * 更新角色的权限
     */
    @PreAuthorize("hasRole('超级管理员')")
    @PutMapping("/put/roleAuth/{roleId}")
    public ResultSetEntity<Auth> putRoleAuth(@PathVariable("roleId") Integer roleId,
                                             @RequestBody Map<String,List<Integer>> idMap) {
        authService.removeRoleAuthByRoleId(roleId);

        List<Integer> authIdList = idMap.get("authIdArr");
        if (authIdList != null && authIdList.size() > 0) {
            authService.saveRoleAuth(roleId,authIdList);
        }
        return ResultSetEntity.succeedNoData();
    }
}
