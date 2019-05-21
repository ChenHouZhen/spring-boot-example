package com.chenhz.neo4j.repository;

import com.chenhz.neo4j.domain.DynamicNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DynamicNodeRepository extends Neo4jRepository<DynamicNode, Long> {


}
