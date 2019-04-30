package com.chenhz.view.controller;

import com.chenhz.common.entity.R;
import com.chenhz.view.entity.Menus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("hello")
public class HelloController {

    @GetMapping("/say")
    public R say(String name){
        return R.ok("hi!"+ name);
    }

    @GetMapping("/menus")
    public R menus(){
        Menus m1 = new Menus();
        m1.setId(1);
        m1.setName("第1季");

        Menus m2 = new Menus();
        m2.setId(2);
        m2.setName("第2季");


        Menus m21 = new Menus();
        m21.setId(21);
        m21.setName("第2-1季");

        Menus m22 = new Menus();
        m22.setId(22);
        m22.setName("第2-2季");

        Menus m11 = new Menus();
        m11.setId(11);
        m11.setName("第1-1季");

        List<Menus> list = new ArrayList<>();
        list.add(m11);
        List<Menus> list2 = new ArrayList<>();
        list2.add(m21);
        list2.add(m22);

        m1.setChinds(list);
        m2.setChinds(list2);

        List<Menus> all = new ArrayList<>();
        all.add(m1);
        all.add(m2);

        return R.ok().put("data",all);

    }
}
