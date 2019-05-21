package com.chenhz.neo4j.controller;

import com.chenhz.neo4j.domain.DynamicNode;
import com.chenhz.neo4j.repository.DynamicNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dynamic")
public class DynamicNodeController {

    @Autowired
    private DynamicNodeRepository dynamicNodeRepository;


    @GetMapping("/init")
    public Iterable<DynamicNode> init() {

        //clean work
        dynamicNodeRepository.deleteAll();


        Map<String,Object> m1 = new HashMap<>();
        m1.put("name","小明");
        m1.put("age",12);
        DynamicNode d1 = new DynamicNode("ChengLongP",m1);


        Map<String,Object> m2 = new HashMap<>();
        m2.put("name","小紅");
        m2.put("age",17);
        DynamicNode d2 = new DynamicNode("长城B",m2);


        Map<String,Object> m3 = new HashMap<>();
        m3.put("name","小lan");
        m3.put("age",17);
        m3.put("sex",1);
        DynamicNode d3 = new DynamicNode("长城A",m3);


        dynamicNodeRepository.save(d1);
        dynamicNodeRepository.save(d2);
        dynamicNodeRepository.save(d3);

        return dynamicNodeRepository.findAll();


    }
}
