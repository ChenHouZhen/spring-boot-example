package com.chenhz.neo4j.controller;

import com.chenhz.common.entity.R;
import com.chenhz.neo4j.domain.Knowledge;
import com.chenhz.neo4j.repository.KnowledgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {


    @Autowired
    KnowledgeRepository knowledgeRepository;



    @GetMapping("/bind")
    public R bind(long preId, long posId){
        knowledgeRepository.bind(preId,posId);
        return R.ok();
    }


    @GetMapping("/all")
    public Iterable<Knowledge> all(){
        return knowledgeRepository.findAll();
    }


    @GetMapping("/graph")
    public Collection<Knowledge> graph(@RequestParam(value = "limit",required = false) Integer limit){
        return knowledgeRepository.graph(limit == null ? 100 : limit);
    }

    @GetMapping("/init")
    public Iterable<Knowledge> init() {

        Knowledge k1 = new Knowledge("知识点 1");
        Knowledge k11 = new Knowledge("知识点 1-1");
        Knowledge k12 = new Knowledge("知识点 1-2");
        Knowledge k13 = new Knowledge("知识点 1-3");
        Knowledge k111 = new Knowledge("知识点 1-1-1");
        Knowledge k112 = new Knowledge("知识点 1-1-2");
        Knowledge k121 = new Knowledge("知识点 1-2-1");


        k1.add(k11);
        k1.add(k12);
        k1.add(k13);

        k11.add(k111);
        k11.add(k112);

        k12.add(k121);

        knowledgeRepository.save(k1);
        knowledgeRepository.save(k11);
        knowledgeRepository.save(k12);
        knowledgeRepository.save(k13);
        knowledgeRepository.save(k111);
        knowledgeRepository.save(k112);
        knowledgeRepository.save(k121);

        return knowledgeRepository.findAll();

    }
}
