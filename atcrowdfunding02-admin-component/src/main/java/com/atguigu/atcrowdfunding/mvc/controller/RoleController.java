package com.atguigu.atcrowdfunding.mvc.controller;

import com.atguigu.atcrowdfunding.constant.CrowdAdminConstant;
import com.atguigu.atcrowdfunding.entity.Role;
import com.atguigu.atcrowdfunding.service.RoleService;
import com.atguigu.atcrowdfunding.utils.PageUtils;
import com.atguigu.atcrowdfunding.utils.ResultSetEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zbystart
 * @create 2021-02-09 15:17
 */
@Controller
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
    @PreAuthorize("hasAnyAuthority('role:get') or hasAnyRole('部长','超级管理员')")
    @GetMapping("/roleList/{pageNum}")
    @ResponseBody
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
    @PreAuthorize("(hasRole('部长操作者') and hasAnyAuthority('role:delete')) or hasRole('超级管理员')")
    @DeleteMapping("/delete/role")
    @ResponseBody
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
    @PreAuthorize("(hasRole('部长操作者') and hasAnyAuthority('role:update')) or hasRole('超级管理员')")
    @PutMapping("/update/role")
    @ResponseBody
    public ResultSetEntity roleUpdate(Role role){
        roleService.putRoleById(role);
        return new ResultSetEntity<>("200","修改成功",null);
    }

    /**
     * 回显用户已经具有的角色
     * @param adminId
     * @param pageNum
     * @param querycondition
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('超级管理员')")
    @GetMapping("/authority/role/{id}")
    public String authorityRole(@PathVariable("id") Integer adminId,
                                @RequestParam("pageNum") Integer pageNum,
                                @RequestParam("querycondition") String querycondition,
                                Model model) {
        List<Role> ownRoleList = roleService.getAdminAuthorityRoleByIdIsYes(adminId);
        List<Role> noOwnRoleList = roleService.getAdminAuthorityRoleByIdIsNo(adminId);
        model.addAttribute("ownRoleList",ownRoleList);
        model.addAttribute("noOwnRoleList",noOwnRoleList);
        model.addAttribute("adminId",adminId);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("querycondition",querycondition);
        return "admin/assignRole";
    }

    /**
     * 删除已分配的角色
     * @param idMap
     * @return
     */
    @PreAuthorize("(hasRole('部长操作者') and hasAnyAuthority('role:delete')) or hasRole('超级管理员')")
    @PostMapping("authority/delete/role")
    @ResponseBody
    public ResultSetEntity authorityDeleteRole(@RequestBody Map<String,List<Integer>> idMap) {
        List<Integer> adminIdList = idMap.get("adminId");
        Integer adminId = adminIdList.get(0);
        List<Integer> roleIdList = idMap.get("roleIds");
        roleService.removeAdminRoleByAdminIdAndRoleId(adminId,roleIdList);
        return ResultSetEntity.succeedNoData();
    }

    /**
     * 添加已分配的角色
     * @param roleIds
     * @param adminId
     * @param pageNum
     * @param querycondition
     * @return
     */
    @PostMapping("authority/add/role")
    public String authorityAddRole(@RequestParam(value = "roleIds",required = false) List<Integer> roleIds,
                                   @RequestParam("adminId") Integer adminId,
                                   @RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("querycondition") String querycondition) {
        if (roleIds != null && roleIds.size() > 0) {
            roleService.saveAdminRole(adminId,roleIds);
        }
        return "redirect:/admin/userList/" + pageNum + "?queryCondition=" + querycondition ;
    }
    /**
     * 添加数据
     * @param role
     * @return
     */
    @PreAuthorize("(hasRole('部长操作者') and hasAnyAuthority('role:add')) or hasRole('超级管理员')")
    @PostMapping("/add/role")
    @ResponseBody
    public ResultSetEntity roleAdd(Role role){
        roleService.saveRole(role);
        return new ResultSetEntity<>("200","添加成功",null);
    }
}
