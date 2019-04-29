package com.chenhz.view.controller;

import com.chenhz.common.entity.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {

    @GetMapping("/say")
    public R say(String name){
        return R.ok("hi!"+ name);
    }

}
