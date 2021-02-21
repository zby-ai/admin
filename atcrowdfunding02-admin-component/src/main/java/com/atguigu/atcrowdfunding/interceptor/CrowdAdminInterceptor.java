package com.atguigu.atcrowdfunding.interceptor;

import com.atguigu.atcrowdfunding.constant.CrowdAdminConstant;
import com.atguigu.atcrowdfunding.entity.Admin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author zbystart
 * @create 2021-02-07 14:05
 */
public class CrowdAdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        HttpSession session = httpServletRequest.getSession();
        Admin admin = (Admin)session.getAttribute(CrowdAdminConstant.ADMIN_USER_KEY);
        if (admin == null){
            String loginMassage = "请先登录！";
            httpServletRequest.setAttribute(CrowdAdminConstant.ADMIN_LOGIN_MASSAGE,loginMassage);
            httpServletRequest.getRequestDispatcher("/admin/to/login/page").forward(httpServletRequest,httpServletResponse);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
