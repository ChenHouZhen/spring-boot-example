package com.chenhz.neo4j.repository.impl;

import com.chenhz.neo4j.domain.Knowledge;
import com.chenhz.neo4j.domain.Movie;
import com.chenhz.neo4j.repository.CustomizedKnowledgeRepository;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Repository
public class CustomizedKnowledgeRepositoryImpl implements CustomizedKnowledgeRepository {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public List<Knowledge> queryKgByPath(String path) {
        Session session = sessionFactory.openSession();
        String[] titles = pathToTitle(path);
        int size = titles.length;

        StringBuilder cql = new StringBuilder("MATCH ");
        for (int i = 0; i<size; i++) {
            String nextCql = String.format("(n%s:Knowledge{title:\"%s\"})",i,titles[i]);
            if (i != size - 1){
                nextCql +="-->";
            }else {
                nextCql += String.format(" RETURN n%s",i);
            }
            cql.append(nextCql);

        }

        log.info("cql ==> {}",cql.toString());

        List<Knowledge> knowledgeList = new ArrayList<>();
        try ( Transaction transaction = session.beginTransaction()) {
            Map<String, String> map = new HashMap<>();
            Result result = session.query(cql.toString(), map);
            log.info("result ==> {}",result);

            if (result != null) {
                Iterable<Map<String, Object>> itrMap = result.queryResults();
                int i = 0;
                for (Map<String, Object> tmpMap: itrMap) {
                    System.out.println(i + "th map");
                    Set<String> keySet = tmpMap.keySet();
                    for (String key : keySet) {
                        Object obj = tmpMap.get(key);
                        if (obj instanceof Knowledge) {
                            System.out.println("    add a Knowledge obj:" + obj);
                            knowledgeList.add((Knowledge)obj);
                        }
                        System.out.println("    key:" + key + ", Value:" + obj);
                    }
                    i++;
                }
            }
            transaction.commit();
        } catch (Exception e) {
            throw  e;
        }

        return knowledgeList;
    }


     private String[] pathToTitle(String path) {
         return path.split("-");
     }

}
