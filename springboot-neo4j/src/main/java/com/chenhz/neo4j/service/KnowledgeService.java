package com.chenhz.neo4j.service;

import com.chenhz.neo4j.domain.Knowledge;
import com.chenhz.neo4j.domain.KnowledgeRelation;
import com.chenhz.neo4j.repository.KnowledgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class KnowledgeService {


    @Autowired
    private KnowledgeRepository knowledgeRepository;


    private Map<String, Object> toD3Format(Collection<Knowledge> knowledges) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        int i = 0;
        Iterator<Knowledge> result = knowledges.iterator();
        while (result.hasNext()) {
            Knowledge knowledge = result.next();
            int target = i;
            Map<String,Object> map = map("title", knowledge.getTitle(), "label", "knowledge","id",knowledge.getId());
            if (nodes.indexOf(map) == -1){
                nodes.add(map);
                i++;
            }

            for (KnowledgeRelation r : knowledge.getRelations()) {
                // 遍历关系，如果下一级不存在 nodes ,就添加上去，否则就不添加
                Map<String, Object> child = map("title", r.getPos().getTitle(), "label", "knowledge","id",r.getPos().getId());
                int source = nodes.indexOf(child);
                if (source == -1){
                    nodes.add(child);
                    source = i++;
                }
                rels.add(map("source",source,"target",target));
            }
        }
        return map("nodes", nodes, "links", rels);
    }


    private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        return result;
    }


    private Map<String,Object> map(String key1, Object value1, String key2, Object value2,String key3,Object value3){
        Map<String, Object> result = this.map(key1,value1,key2,value2);
        result.put(key3,value3);
        return result;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> graph(int limit) {
        Collection<Knowledge> result = knowledgeRepository.graph(limit);
        return toD3Format(result);
    }

}
