package com.chenhz.session.controller;

import com.chenhz.common.entity.R;
import com.chenhz.common.entity.User;
import com.chenhz.session.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/redis")
public class RedisController {


    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/set/o")
    public R setObject(){
        User u = new User();
        u.setAge(12);
        u.setName("小明");
        u.setPhone("156227580");
        redisUtils.set("spring:redis:object",u);
        return R.ok();
    }

    @PostMapping("/map/o")
    public R mapObject(){
        User u = new User();
        u.setAge(12);
        u.setName("小明");
        u.setPhone("156227580");
        redisUtils.set("spring:redis:object",u);

        Map<String,Object> map = new HashMap<>();
        map.put("user",u);
        map.put("num",2);
        map.put("str","字符串");
        redisUtils.putAllHash("spring:redis:map:object",map);
        return R.ok();
    }


    @GetMapping("/get/o")
    public R getObject(HttpSession ss){
        User u = redisUtils.get("spring:redis:object",User.class);
        return R.ok().put("data",u);
    }




}
