package com.atguigu.atcrowdfunding.mvc.controller;

import com.atguigu.atcrowdfunding.constant.CrowdAdminConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zbystart
 * @create 2021-02-25 12:29
 */
@Controller
@RequestMapping("/admin")
public class PageSkipController {

    @GetMapping("to/error/page")
    public String goErrorPage(@RequestParam(value = "exception",required = false) String exceptionMassage,
                              Model model) {
//        System.out.println(exceptionMassage);
        model.addAttribute(CrowdAdminConstant.EXCEPTION_KEY,exceptionMassage);
        return "error/system-error";
    }
}
