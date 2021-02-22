package com.atguigu.atcrowdfunding.mvc.controller;

import com.atguigu.atcrowdfunding.utils.ResultSetEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zbystart
 * @create 2021-02-06 16:08
 */
@RestController
@RequestMapping("/testjson")
public class TestJsonController {
    @PostMapping("/one")
    public ResultSetEntity testJsonOne(@RequestBody List<Integer> array){
        int i = 10/0;
        for (Integer integer : array){
            System.out.println(integer);
        }
        return ResultSetEntity.succeedNoData();
    }
    @GetMapping("/two")
    public String testJsonTwo(HttpServletRequest request){
        String sss = request.getSession().getServletContext().getRealPath("sss");
        return sss;
    }

}
