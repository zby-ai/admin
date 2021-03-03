package com.atguigu.atcrowdfunding.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zbystart
 * @create 2021-02-24 18:43
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 开启基于注解的权限管理
public class CrowdWebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CrowdUserDetailsService crowdUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {

//        builder
//            .inMemoryAuthentication()    // 在内存中完成账号、密码的检查
//            .withUser("tom")            // 指定账号
//            .password("123123")            // 指定密码
//            .roles("ADMIN","学徒")                // 指定当前用户的角色
//            ;

        builder.userDetailsService(crowdUserDetailsService).passwordEncoder(bCryptPasswordEncoder);


    }


    @Override
    protected void configure(HttpSecurity security) throws Exception {


        security
                .authorizeRequests()            // 对请求进行授权
                .antMatchers("/admin/to/login/page")
                .permitAll()                    // 可以无条件访问
                .antMatchers("/bootstrap/**")	// 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/crowd/**")       // 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/css/**")         // 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/fonts/**")       // 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/img/**")         // 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/jquery/**")      // 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/layer/**")       // 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/script/**")      // 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/ztree/**")       // 针对静态资源进行设置，无条件访问
                .permitAll()
                // 可以无条件访问
                .and()
                .authorizeRequests()            // 对请求进行授权
                .anyRequest()                    // 任意请求
                .authenticated()                // 需要登录以后才可以访问
                .and()
                .formLogin()                    // 使用表单形式登录
                .loginPage("/admin/to/login/page")        // 指定登录页面（如果没有指定会访问SpringSecurity自带的登录页）
                // loginProcessingUrl()方法指定了登录地址，就会覆盖loginPage()方法中设置的默认值/index.jsp POST
                .loginProcessingUrl("/admin/do/login.html")    // 指定提交登录表单的地址
                .usernameParameter("loginAcct")            // 定制登录账号的请求参数名
                .passwordParameter("userPswd")            // 定制登录密码的请求参数名
                .defaultSuccessUrl("/admin/to/main/page")        // 登录成功后前往的地址
                .and()
                .exceptionHandling()                    // 指定异常处理器
                // .accessDeniedPage("/to/no/auth/page.html")    // 访问被拒绝时前往的页面
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        httpServletRequest.setAttribute("exception", e.getMessage());
                        httpServletRequest.getRequestDispatcher("/WEB-INF/view/error/system-error.jsp").forward(httpServletRequest, httpServletResponse);
                    }
                })
                .and()
                .csrf()
                .disable()                                // 禁用CSRF功能  开启CSRF功能 表单提交的方式必须是post 并且要有  name="${_csrf.parameterName}" value="${_csrf.token}"  这俩个固定的参数
                .logout()                                // 开启退出功能
                .logoutUrl("/admin/do/logout.html")            // 指定处理退出请求的URL地址
                .logoutSuccessUrl("/admin/to/login/page")            // 退出成功后前往的地址
                ;
    }
}
