package com.chenhz.neo4j.controller;

import com.chenhz.common.entity.R;
import com.chenhz.neo4j.domain.Knowledge;
import com.chenhz.neo4j.domain.Person;
import com.chenhz.neo4j.domain.Relation;
import com.chenhz.neo4j.repository.KnowledgeRepository;
import com.chenhz.neo4j.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {


    @Autowired
    KnowledgeRepository knowledgeRepository;


    @Autowired
    KnowledgeService knowledgeService;

    @GetMapping("/bind")
    public R bind(long preId, long posId){
        knowledgeRepository.bind(preId,posId);
        return R.ok();
    }

    @GetMapping("/all")
    public Iterable<Knowledge> all(){
        return knowledgeRepository.findAll();
    }


    @PostMapping("/delete")
    public R deleteAll(){
        knowledgeRepository.deleteAll();
        return R.ok();
    }


    @GetMapping("/graph")
    public R graph(@RequestParam(value = "limit",required = false) Integer limit){
        Map<String, Object> data =  knowledgeService.graph(limit == null ? 100 : limit);
        return R.ok().put("data",data);
    }

    @GetMapping("/init")
    public R init() {

        knowledgeRepository.deleteAll();


        Knowledge k1 = new Knowledge("知识点 1");


        Knowledge k11 = new Knowledge("知识点 1-1");
        Knowledge k12 = new Knowledge("知识点 1-2");
        Knowledge k13 = new Knowledge("知识点 1-3");
        Knowledge k111 = new Knowledge("知识点 1-1-1");
        Knowledge k112 = new Knowledge("知识点 1-1-2");
        Knowledge k121 = new Knowledge("知识点 1-2-1");

/*
        Relation r1_11 = new Relation(k1,k11);
        Relation r1_12 = new Relation(k1,k12);
        Relation r1_13 = new Relation(k1,k13);
        Relation r11_111 = new Relation(k11,k111);
        Relation r11_112 = new Relation(k11,k112);
        Relation r12_121 = new Relation(k12,k121);
*/

        k1.addChind(k11);
        k1.addChind(k12);
        k1.addChind(k13);

        k11.addChind(k111);
        k11.addChind(k112);

        k12.addChind(k121);

        knowledgeRepository.save(k1);
        knowledgeRepository.save(k11);
        knowledgeRepository.save(k12);
        knowledgeRepository.save(k13);
        knowledgeRepository.save(k111);
        knowledgeRepository.save(k112);
        knowledgeRepository.save(k121);

        return R.ok();

    }
}
