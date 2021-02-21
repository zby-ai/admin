package com.atguigu.atcrowdfunding.mvc.controller;

import com.atguigu.atcrowdfunding.constant.CrowdAdminConstant;
import com.atguigu.atcrowdfunding.entity.Admin;
import com.atguigu.atcrowdfunding.service.AdminService;
import com.atguigu.atcrowdfunding.utils.PageUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


/**
 * @author zbystart
 * @create 2021-02-06 12:22
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;


    @PostMapping("/login")
    public String login(@RequestParam("loginAcct") String loginAcct,
                        @RequestParam("userPswd") String userPswd,
                        HttpSession session){
        Admin admin = adminService.getAdminByLoginAcctAndUserPswd(loginAcct, userPswd);
        session.setAttribute(CrowdAdminConstant.ADMIN_USER_KEY,admin);
        return "redirect:/admin/to/main/page";
    }

    /**
     * 注销账户
     * @param session
     * @return
     */
    @GetMapping("/loginout")
    public String loginout(HttpSession session){
        session.invalidate();
        return "redirect:/admin/to/login/page";
    }

    /**
     * 分页查询用户列表
     * @param pageNum
     * @param queryCondition
     * @param model
     * @return
     */
    @GetMapping("/userList/{pageNum}")
    public String userList(@PathVariable(value = "pageNum") Integer pageNum,
                           @RequestParam(value = "queryCondition",required = false)String queryCondition,
                           Model model){
        String path = "admin/userList";
        // 带条件参数的分页查询
        if (queryCondition != null && queryCondition.length() > 0){
            PageInfo<Admin> pageInfo = adminService.getAdminListByCondition(queryCondition, pageNum, CrowdAdminConstant.pageSize,CrowdAdminConstant.pageFootCount,model);
            if (pageInfo == null){
                return "admin/user";
            }
            adminPage(model,pageInfo,path,queryCondition);
            model.addAttribute(CrowdAdminConstant.ADMIN_QUERYCONDITION,queryCondition);
            return "admin/user";
        }
        //普通分页查询
        PageInfo<Admin> pageInfo = adminService.getAdminListByPage(pageNum, CrowdAdminConstant.pageSize,CrowdAdminConstant.pageFootCount);
        adminPage(model,pageInfo,path,null);
        return "admin/user";
    }

    /**
     * 获取要修改的账户信息,用于表单的回显
     * @param adminId
     * @param page
     * @param queryCondition
     * @param model
     * @return
     */
    @GetMapping("/get/update/massage/{page}")
    public String getAdminMassageToUpdate(@RequestParam("adminId")Integer adminId,
                                          @PathVariable("page")Integer page,
                                          @RequestParam(value = "queryCondition",required = false) String queryCondition,
                                          Model model){
        // 表单回显
        Admin admin = adminService.getAdminById(adminId);
        model.addAttribute(CrowdAdminConstant.ADMIN_ADMIN,admin);
        model.addAttribute(CrowdAdminConstant.ADMIN_PAGE_MASSAGE,page);
        // 转发条件参数
        if (queryCondition != null){
            model.addAttribute(CrowdAdminConstant.ADMIN_QUERYCONDITION,queryCondition);
        }
        return "admin/edit";
    }

    /**
     * 修改账户信息
     * @param page
     * @param queryCondition
     * @param redirectAttributes
     * @param admin
     * @return
     */
    @PutMapping("/update/{page}")
    public String updateAdmin(@PathVariable("page")Integer page,
                              @RequestParam(value = "queryCondition",required = false)String queryCondition,
                              RedirectAttributes redirectAttributes,
                              Admin admin){
        adminService.putAdminById(admin);
        if (queryCondition != null) {
            redirectAttributes.addAttribute(CrowdAdminConstant.ADMIN_QUERYCONDITION,queryCondition);
            return "redirect:/admin/userList/" + page;
        }
        return "redirect:/admin/userList/" + page;
    }

    /**
     * 添加账户信息
     * @param admin
     * @return
     */
    @PostMapping("/add")
    public String add(Admin admin){
        adminService.saveAdmin(admin);
//        return "redirect:/admin/userList/" + (pages + 1);
        return "redirect:/admin/userList/" + Integer.MAX_VALUE;
    }
//
//    @GetMapping("/to/add/page")
//    public String toAddPage(@RequestParam("pages")Integer pages,Model model){
//        model.addAttribute("pages",pages);
//        return "admin/add";
//    }

    /**
     * 删除账户信息
     * @param adminId
     * @param pageNum
     * @param queryCondition
     * @return
     */
    @DeleteMapping("/delete/{pageNum}")
    public String delete(@RequestParam("adminId")String adminId,
                         @PathVariable("pageNum")Integer pageNum,
                         @RequestParam(value = "queryCondition",required = false)String queryCondition){

        if (adminId != null && adminId.length() > 0){
            if (adminId.contains(",")) {
                String[] adminIds = adminId.split(",");
                adminService.removeBatchAdminById(adminIds);
            }else {
                int id = Integer.parseInt(adminId);
                adminService.removeAdminById(id);
            }
        }
        if (queryCondition != null && queryCondition.length() > 0) {
            return "redirect:/admin/userList/" + pageNum + "?queryCondition=" + queryCondition;
        }
        return "redirect:/admin/userList/" + pageNum;
    }

    /**
     * 拼接页脚
     * @param model
     * @param pageInfo
     * @param path
     * @param queryCondition
     */
    private void adminPage(Model model,PageInfo<Admin> pageInfo,String path,String queryCondition){
        String pageFoot = "pageFoot";
        String admins = "admins";
        String page = PageUtils.getPage(pageInfo, path,queryCondition);
        model.addAttribute(admins,pageInfo.getList());
        model.addAttribute(pageFoot,page);
        model.addAttribute(CrowdAdminConstant.ADMIN_PAGEINFO,pageInfo);
    }

}
