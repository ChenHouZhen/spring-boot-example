package com.chenhz.neo4j.service;

import com.chenhz.neo4j.domain.Knowledge;
import com.chenhz.neo4j.repository.KnowledgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class KnowledgeService {


    @Autowired
    private KnowledgeRepository knowledgeRepository;


 /*   private Map<String, Object> toD3Format(Collection<Knowledge> knowledges) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        int i = 0;
        Iterator<Knowledge> result = knowledges.iterator();
        while (result.hasNext()) {
            Knowledge knowledge = result.next();
            nodes.add(map("title", knowledge.getTitle(), "label", "Knowledge"));
            int target = i;
            i++;
            for (Knowledge k : knowledge.getChildren()) {
                Map<String, Object> actor = map("title", k.getPerson().getName(), "label", "actor");
                int source = nodes.indexOf(actor);
                if (source == -1) {
                    nodes.add(actor);
                    source = i++;
                }
                rels.add(map("source", source, "target", target));
            }
        }
        return map("nodes", nodes, "links", rels);
    }*/

    private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        return result;
    }

  /*  @Transactional(readOnly = true)
    public Map<String, Object> graph(int limit) {
        Collection<Knowledge> result = knowledgeRepository.graph(limit);
        return toD3Format(result);
    }*/

}
