package com.chenhz.neo4j.repository;

import com.chenhz.neo4j.domain.Knowledge;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface KnowledgeRepository  extends Neo4jRepository<Knowledge, Long> {


    // 注意 ： id 不能 直接用 {id：{id_value}} 或 m.id= id_value
    @Query("MATCH (m:Knowledge),(a:Knowledge) WHERE id(m)={preId} AND id(a)={posId} CREATE (m)-[r:NEW]->(a)")
    void bind(@Param("preId") long preId,@Param("posId") long posId);


    @Query("MATCH (m:Knowledge)<-[r:NEW]-(a:Knowledge) RETURN m,r,a LIMIT {limit}")
    Collection<Knowledge> graph(@Param("limit") int limit);
}
