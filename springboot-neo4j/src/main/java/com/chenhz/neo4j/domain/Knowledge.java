package com.chenhz.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@Data
@NodeEntity
public class Knowledge {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    public Knowledge(){
        if (children == null){
            this.children = new ArrayList<>();
        }
    }

    public Knowledge(String title){
        if (children == null){
            this.children = new ArrayList<>();
        }
        this.title = title;
    }

    @JsonIgnoreProperties("models")
    @Relationship(type = "ACTED_IN", direction = Relationship.OUTGOING)
    private List<Knowledge> children;


    public void add(Knowledge k){
        this.children.add(k);
    }
}
