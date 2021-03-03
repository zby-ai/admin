package com.atguigu.atcrowdfunding.mvc.config;

import com.atguigu.atcrowdfunding.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * @author zbystart
 * @create 2021-02-24 19:36
 */
public class SecurityAdmin extends User {

    private Admin oldAdmin;

    public SecurityAdmin(Admin oldAdmin, List<GrantedAuthority> authorities) {
        super(oldAdmin.getUserName(), oldAdmin.getUserPswd(), authorities);
        this.oldAdmin = oldAdmin;
        this.oldAdmin.setUserPswd(null);
    }

    public Admin getOldAdmin() {
        return oldAdmin;
    }

    public void setOldAdmin(Admin oldAdmin) {
        this.oldAdmin = oldAdmin;
    }
}
