package com.atguigu.atcrowdfunding.mvc.controller;

import com.atguigu.atcrowdfunding.entity.Menu;
import com.atguigu.atcrowdfunding.service.MenuService;
import com.atguigu.atcrowdfunding.utils.ResultSetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author zbystart
 * @create 2021-02-22 14:02
 */
@RestController
@RequestMapping("/admin/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 查询树形结构数据
     * @return
     */
    @GetMapping("/list")
    public ResultSetEntity<Menu> list() {
        // 获取所有的树型结构数据
        List<Menu> menus = menuService.getMenuList();
        // 根节点
        Menu root = null;

        HashMap<Integer, Menu> menuMap = new HashMap<>();
        // 遍历所有的数据，并保存到map集合中
        for (Menu menu : menus) {
            menuMap.put(menu.getId(),menu);
        }
        // 装载子节点
        for (Menu menu : menus) {
            Integer pid = menu.getPid();
            // 找出父节点
            if (pid == null) {
                root = menu;
                continue;
            }
            Menu parentMenu = menuMap.get(pid);
            parentMenu.getChildren().add(menu);
        }
        return ResultSetEntity.succeedYesData("查询成功！",root);
    }

    /**
     * 添加子节点
     */
    @PostMapping("/add")
    public ResultSetEntity<Menu> add(Menu menu) {
        menuService.saveMenu(menu);
        return ResultSetEntity.succeedNoData();
    }

    /**
     * 删除子节点
     */
    @DeleteMapping("/delete/{id}")
    public ResultSetEntity<Menu> remove(@PathVariable("id")Integer id) {
        menuService.removeMenuById(id);
        return ResultSetEntity.succeedNoData();
    }

    /**
     * 更新节点
     */
    @PutMapping("/put")
    public ResultSetEntity<Menu> put(Menu menu) {
        menuService.putMenuById(menu);
        return ResultSetEntity.succeedNoData();
    }
}
