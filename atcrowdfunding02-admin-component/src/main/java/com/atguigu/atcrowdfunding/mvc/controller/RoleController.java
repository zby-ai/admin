package com.atguigu.atcrowdfunding.mvc.controller;

import com.atguigu.atcrowdfunding.constant.CrowdAdminConstant;
import com.atguigu.atcrowdfunding.entity.Role;
import com.atguigu.atcrowdfunding.service.RoleService;
import com.atguigu.atcrowdfunding.utils.PageUtils;
import com.atguigu.atcrowdfunding.utils.ResultSetEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

/**
 * @author zbystart
 * @create 2021-02-09 15:17
 */
@RestController
@RequestMapping("/admin")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 获取分页查询信息
     * @param pageNum
     * @param queryCondition
     * @param model
     * @return
     */
    @GetMapping("/roleList/{pageNum}")
    public ResultSetEntity<HashMap<String, Object>> roleList(@PathVariable("pageNum") Integer pageNum,
                                                             @RequestParam(value = "queryCondition",required = false)String queryCondition,
                                                             Model model
                                          ) {
        HashMap<String, Object> hashMap = new HashMap<>();
        String path = "admin/roleList";
        String pageFoot = "pageFoot";
        Integer pageSize = CrowdAdminConstant.pageSize;
        Integer pageFootCount = CrowdAdminConstant.pageFootCount;
        PageInfo<Role> pageInfo = null;
        // 带有条件的查询
        if (queryCondition != null && queryCondition.length() > 0) {

            pageInfo = roleService.getRoleList(pageNum, pageSize,pageFootCount, queryCondition);
            if (pageInfo == null) {
                return new ResultSetEntity<>("404","没有查询到符合条件的数据!",null);
            }
            hashMap.put(pageFoot,PageUtils.getPage(pageInfo,path,queryCondition));
            hashMap.put(CrowdAdminConstant.ADMIN_QUERYCONDITION,queryCondition);
        }else {
            // 不带条件的查询
            pageInfo = roleService.getRoleList(pageNum,pageSize,pageFootCount,null);
            hashMap.put(pageFoot,PageUtils.getPage(pageInfo,path,null));
        }
        hashMap.put(CrowdAdminConstant.ADMIN_PAGEINFO,pageInfo);
        hashMap.put(CrowdAdminConstant.ADMIN_QUERYCONDITION,null);
        return new ResultSetEntity<>("200","查询成功！",hashMap);
    }

    /**
     * 删除
     * @param roleId
     * @return
     */
    @DeleteMapping("/delete/role")
    public ResultSetEntity roleDelete(@RequestParam("roleId")String roleId){
        if (roleId == "") {
            return new ResultSetEntity<>("500","请选择要删除的角色！",null);
        }
        if ( roleId.contains(",")) {
            String[] roleIds = roleId.split(",");
            roleService.batchRemoveRoleById(roleIds);
        }else {
            roleService.removeRoleById(Integer.parseInt(roleId));
        }
        return new ResultSetEntity<>("200","删除成功",null);
    }

    /**
     * 修改数据
     * @param role
     * @return
     */
    @PutMapping("/update/role")
    public ResultSetEntity roleUpdate(Role role){
        roleService.putRoleById(role);
        return new ResultSetEntity<>("200","修改成功",null);
    }

    /**
     * 添加数据
     * @param role
     * @return
     */
    @PostMapping("/add/role")
    public ResultSetEntity roleAdd(Role role){
        roleService.saveRole(role);
        return new ResultSetEntity<>("200","添加成功",null);
    }
}
