package com.zjut.school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 张璐杰
 * 2018/4/17 12:15
 */
@Controller
@RequestMapping("/web")
public class PageRedirectController {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
