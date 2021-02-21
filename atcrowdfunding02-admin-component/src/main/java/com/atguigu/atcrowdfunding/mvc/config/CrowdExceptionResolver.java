package com.atguigu.atcrowdfunding.mvc.config;

import com.atguigu.atcrowdfunding.constant.CrowdAdminConstant;
import com.atguigu.atcrowdfunding.exception.CrowdAdminLoginAcctRedoException;
import com.atguigu.atcrowdfunding.exception.CrowdAdminLoginException;
import com.atguigu.atcrowdfunding.exception.CrowdMd5Exception;
import com.atguigu.atcrowdfunding.utils.CrowdUtils;
import com.atguigu.atcrowdfunding.utils.ResultSetEntity;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义异常处理器
 * @author zbystart
 * @create 2021-02-06 15:56
 */
@ControllerAdvice
public class CrowdExceptionResolver {

    @ExceptionHandler(CrowdAdminLoginAcctRedoException.class)
    public ModelAndView crowdAdminLoginAcctRedoExceptionHanlder(CrowdAdminLoginAcctRedoException exception,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        String viewName = "admin/add";
        ModelAndView modelAndView = commonExceptionHanlder(viewName, exception, request, response);
        if (modelAndView != null){
            String pages = "pages";
            modelAndView.addObject(pages,request.getParameter(pages));
        }
        return modelAndView;
    }

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView nullPointerExceptionHanlder(NullPointerException exception,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        String viewName = "error/system-error";
        return commonExceptionHanlder(viewName,exception,request,response);
    }

    @ExceptionHandler(ArithmeticException.class)
    public ModelAndView arithmeticExceptionHanlder(ArithmeticException exception,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws IOException {
        String viewName = "error/system-error";
        ModelAndView modelAndView = commonExceptionHanlder(viewName, exception, request, response);
        if (modelAndView != null){
            String pages = "pages";
            modelAndView.addObject(pages,request.getParameter(pages));
        }
        return modelAndView;
    }

    @ExceptionHandler(CrowdAdminLoginException.class)
    public ModelAndView arithmeticExceptionHanlder(CrowdAdminLoginException exception,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws IOException {
        String viewName = "admin/admin-login";
        ModelAndView modelAndView = commonExceptionHanlder(viewName, exception, request, response);
        if (modelAndView != null) {
            String loginAcct = "loginAcct";
            modelAndView.addObject(loginAcct,request.getParameter(loginAcct));
        }
        return modelAndView;
    }

    @ExceptionHandler(CrowdMd5Exception.class)
    public ModelAndView arithmeticExceptionHanlder(CrowdMd5Exception exception,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws IOException {
        String viewName = "error/system-error";
        return commonExceptionHanlder(viewName,exception,request,response);
    }






    private ModelAndView commonExceptionHanlder(String viewName,
                                                Exception exception,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {

        boolean type = CrowdUtils.judgeRequestType(request);
        System.out.println(type);
        if (type){

            ResultSetEntity<Object> resultSetEntity = ResultSetEntity.failureYesData(exception.getMessage());
            Gson gson = new Gson();
            String jsonString = gson.toJson(resultSetEntity);
            response.getWriter().write(jsonString);
            return null;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CrowdAdminConstant.EXCEPTION_KEY,exception);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
