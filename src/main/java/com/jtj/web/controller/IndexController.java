package com.jtj.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2018/3/13.
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "redirect:login";
    }
    @GetMapping("/login")
    public String login(){
    	return "login";
    }
    @GetMapping("/index")
    public String indexV(){
    	return "index";
    }

}
