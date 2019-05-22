package com.chenhz.neo4j.repository;

import com.chenhz.neo4j.domain.DynamicNode;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface DynamicNodeRepository extends Neo4jRepository<DynamicNode, Long> {

    Iterable<DynamicNode> findByTitle(@Param("title") String title);


//    类型要保持一直，String 类型无法查出数据
    @Query("MATCH (n:DynamicNode{sex:{sex}}) RETURN n ")
    Iterable<DynamicNode> listBySex(@Param("sex") int sex);
}
