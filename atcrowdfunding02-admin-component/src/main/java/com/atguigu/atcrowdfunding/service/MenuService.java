package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.entity.Menu;

import java.util.List;

/**
 * @author zbystart
 * @create 2021-02-22 14:03
 */
public interface MenuService {
    void saveMenu(Menu menu);
    void putMenuById(Menu menu);
    void removeMenuById(Integer id);
    List<Menu> getMenuList();
}
