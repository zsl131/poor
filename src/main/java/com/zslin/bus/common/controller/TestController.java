package com.zslin.bus.common.controller;

import com.zslin.bus.tools.PersonalXhTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zsl on 2019/4/28.
 */
@RestController
@RequestMapping(value = "test")
public class TestController {

    @Autowired
    private PersonalXhTools personalXhTools;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, String p) {
        return "连接正常，参数："+p;
    }

    @RequestMapping(value = "processXh")
    public String processXh() {
        Long start = System.currentTimeMillis();
        personalXhTools.resetXh();
        Long end = System.currentTimeMillis();
        Long use = (end - start) /1000;
        return "处理完成，用时 ： "+use + " 秒。";
    }
}
