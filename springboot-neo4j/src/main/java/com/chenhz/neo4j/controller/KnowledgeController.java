package com.chenhz.neo4j.controller;

import com.chenhz.common.entity.R;
import com.chenhz.neo4j.domain.Knowledge;
import com.chenhz.neo4j.domain.Property;
import com.chenhz.neo4j.repository.KnowledgeRepository;
import com.chenhz.neo4j.service.KnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmind.core.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
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


    @GetMapping("/children")
    public Iterable<Knowledge> children(@RequestParam Integer id){
        return knowledgeRepository.children(id);
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

    @PostMapping("/load")
    public R load(@RequestParam("file") MultipartFile multipartFile) throws IOException, CoreException {
        long startTime = System.currentTimeMillis();


        IWorkbookBuilder builder = Core.getWorkbookBuilder();
        IWorkbook book = builder.loadFromStream(multipartFile.getInputStream());
        ITopic rootItopic = book.getPrimarySheet().getRootTopic();

        List<Knowledge> result = new ArrayList<>();

        Knowledge k = new Knowledge();
        k.setTitle(rootItopic.getTitleText().trim());
        result.add(k);
        this.next(rootItopic,k,result,"---");

        System.out.println(result);
        long endTime = System.currentTimeMillis();
        knowledgeRepository.saveAll(result);
        log.info("消耗的时间：{} ms", (endTime - startTime));
        return R.ok();
    }


    private void next(ITopic fatherITopic,Knowledge fatherKnowledge,List<Knowledge> all,String line){

        List<ITopic> children = fatherITopic.getAllChildren();
        if (CollectionUtils.isEmpty(children)){
            return;
        }

        String nowLine = line;
        line += "---";
        System.out.println(nowLine + fatherITopic.getTitleText().trim());

        for (ITopic i: children){
            Knowledge k = new Knowledge();
            k.setTitle(i.getTitleText().trim());
            fatherKnowledge.addChind(k);
            all.add(k);
            this.next(i,k,all,line);
        }
    }


    @GetMapping("/init")
    public R init() {

        knowledgeRepository.deleteAll();


        List<Property> properties1 = new ArrayList<>();
        Property p1 = new Property(1,"属性1-1");
        Property p2 = new Property(1,"属性1-2");
        Property p3 = new Property(1,"属性1-3");
        properties1.add(p1);
        properties1.add(p2);
        properties1.add(p3);
        Knowledge k1 = new Knowledge("知识点 1");
        //k1.setProperties(properties1);
        //k1.setProperty(p1);

        Knowledge k11 = new Knowledge("知识点 1-1");
        Knowledge k12 = new Knowledge("知识点 1-2");
        Knowledge k13 = new Knowledge("知识点 1-3");
        Knowledge k111 = new Knowledge("知识点 1-1-1");
        Knowledge k112 = new Knowledge("知识点 1-1-2");
        Knowledge k121 = new Knowledge("知识点 1-2-1");

/*
        KnowledgeRelation r1_11 = new KnowledgeRelation(k1,k11);
        KnowledgeRelation r1_12 = new KnowledgeRelation(k1,k12);
        KnowledgeRelation r1_13 = new KnowledgeRelation(k1,k13);
        KnowledgeRelation r11_111 = new KnowledgeRelation(k11,k111);
        KnowledgeRelation r11_112 = new KnowledgeRelation(k11,k112);
        KnowledgeRelation r12_121 = new KnowledgeRelation(k12,k121);
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
