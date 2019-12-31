package com.chenhz.neo4j.controller;

import com.chenhz.common.entity.R;
import com.chenhz.neo4j.domain.Knowledge;
import com.chenhz.neo4j.repository.KnowledgeRepository;
import com.chenhz.neo4j.service.KnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmind.core.*;
import org.xmind.core.marker.IMarkerRef;
import org.xmind.core.style.IStyle;
import org.xmind.core.style.IStyleSheet;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    @GetMapping("/graph3D")
    public R graph3D(@RequestParam(value = "id") Integer id){
        Collection<Knowledge> data =  knowledgeRepository.graph3D(id,2);
        return R.ok().put("data",data);
    }


    @PostMapping("/load")
    public R load(@RequestParam("file") MultipartFile multipartFile) throws IOException, CoreException {
        long startTime = System.currentTimeMillis();


        IWorkbookBuilder builder = Core.getWorkbookBuilder();
        IWorkbook book = builder.loadFromStream(multipartFile.getInputStream());
        ISheet iSheet = book.getPrimarySheet();
        ITopic rootItopic = iSheet.getRootTopic();
        IStyleSheet iStyleSheet = book.getStyleSheet();
        Set<IStyle> iStyles = iStyleSheet.getStyles("normal-styles");
        List<String> allColors = new ArrayList<>();
        Set<String> colors = new HashSet<>();
        for (IStyle iStyle:iStyles) {
            String color =  iStyle.getProperty("svg:fill");
            colors.add(color);
        }

        System.out.println("颜色数量："+colors.size());
        System.out.println("颜色种类："+ colors);

        List<Knowledge> result = new ArrayList<>();

        Knowledge k = new Knowledge();
        k.setTitle(rootItopic.getTitleText().trim());
        k.setCreateTime(new Date());
        k.setUpdateTime(System.currentTimeMillis());
        result.add(k);

        this.next(rootItopic,k,result,allColors,iStyleSheet);

        Map<Object, List<String>> data = allColors.stream().collect(Collectors.groupingBy(String::valueOf));
        data.forEach((key,val) -> {
            System.out.println(key + "  "+ val.size());
        });


        long endTime = System.currentTimeMillis();
        knowledgeRepository.saveAll(result);
        log.info(">>>>> 消耗的时间：{} ms", (endTime - startTime));
        log.info("导入知识点数量：[{}] 个， 消耗的时间：[{}] ms",result.size(), (endTime - startTime));
        return R.ok();
    }


    private void next(ITopic fatherITopic,Knowledge fatherKnowledge,List<Knowledge> all,List<String> colors,IStyleSheet iStyleSheet){

        List<ITopic> children = fatherITopic.getAllChildren();
//        System.out.println(fatherITopic.getTitleText()+" : "+fatherITopic.getStyleId());
        Set<IMarkerRef> iMarkerRefs  =  fatherITopic.getMarkerRefs();
//        for (IMarkerRef iMarkerRef:iMarkerRefs) {
//            System.out.println("优先级："+iMarkerRef.getMarkerId());
//        }
        IStyle iStyle = iStyleSheet.findStyle(fatherITopic.getStyleId());
        String iColor = iStyle != null ? iStyle.getProperty("svg:fill") : "无颜色";
        colors.add(iColor);

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
            this.next(i,k,all,colors,iStyleSheet);
        }
    }

}
