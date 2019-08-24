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
import java.util.Date;
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

    @GetMapping("/path")
    public List<Knowledge> path(@RequestParam String path){
        return knowledgeRepository.queryKgByPath(path);
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
        k.setCreateTime(new Date());
        k.setUpdateTime(System.currentTimeMillis());
        result.add(k);

        this.next(rootItopic,k,result);

        long endTime = System.currentTimeMillis();
        knowledgeRepository.saveAll(result);
        log.info(">>>>> 消耗的时间：{} ms", (endTime - startTime));
        return R.ok();
    }


    private void next(ITopic fatherITopic,Knowledge fatherKnowledge,List<Knowledge> all){

        List<ITopic> children = fatherITopic.getAllChildren();
        if (CollectionUtils.isEmpty(children)){
            return;
        }

        for (ITopic i: children){
            Knowledge k = new Knowledge();
            k.setTitle(i.getTitleText().trim());
            k.setCreateTime(new Date());
            k.setUpdateTime(System.currentTimeMillis());

            fatherKnowledge.addChind(k);
            all.add(k);
            this.next(i,k,all);
        }
    }

}
