package com.chenhz.neo4j.repository;

import com.chenhz.neo4j.domain.DynamicNode;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface DynamicNodeRepository extends Neo4jRepository<DynamicNode, Long> {

    Iterable<DynamicNode> findByTitle(@Param("title") String title);
}
