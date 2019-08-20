package com.chenhz.neo4j.repository;

import com.chenhz.neo4j.domain.Knowledge;

import java.util.List;

public interface CustomizedKnowledgeRepository {

    List<Knowledge> queryKgByPath(String path);
}
