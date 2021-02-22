package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.entity.Menu;
import com.atguigu.atcrowdfunding.entity.MenuExample;
import com.atguigu.atcrowdfunding.mapper.MenuMapper;
import com.atguigu.atcrowdfunding.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zbystart
 * @create 2021-02-22 14:06
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void putMenuById(Menu menu) {
        menuMapper.updateByPrimaryKey(menu);
    }

    @Override
    public void removeMenuById(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Menu> getMenuList() {
        return menuMapper.selectByExample(new MenuExample());
    }
}
