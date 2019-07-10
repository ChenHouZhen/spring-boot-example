package com.chenhz.session.controller;


import com.chenhz.common.entity.R;
import com.chenhz.common.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@Api(tags = "Session 操作接口")
public class SessionController {

    @GetMapping("/info")
    @ApiOperation(value = "获取信息")
    public R info(HttpSession ss){
       return R.ok().put("session",ss);
    }

    @GetMapping("/id")
    public R id(HttpSession ss){
        return R.ok().put("session_id",ss.getId());
    }


    @PostMapping("/set")
    public R set(HttpSession ss,String key,String val){
        ss.setAttribute(key,val);
        return R.ok();
    }


    @PostMapping("/set/o")
    public R setObject(HttpSession ss){
        User u = new User();
        u.setAge(12);
        u.setName("小明");
        u.setPhone("156227580");
        ss.setAttribute("user",u);
        return R.ok();
    }



    @PostMapping("/uid")
    public String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}
