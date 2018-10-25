package com.dadazao.web;

import com.dadazao.api.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    Environment environment;

    @GetMapping("/blog/list")
    public Result blogList() {
        Result result = new Result();
        result.setCode("200");
        result.setResult(blogService.list());
        return result;
    }

    @GetMapping("/env")
    public Result configCenterDemo(String key) {
        Result result = new Result();
        result.setCode("200");
        result.setResult(environment.getProperty(key, "nil"));
        return result;
    }
}
