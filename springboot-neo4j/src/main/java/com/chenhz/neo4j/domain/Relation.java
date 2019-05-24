package com.chenhz.neo4j.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@RelationshipEntity(type = "ACTED_IN")
public class Relation {

    @Id
    @GeneratedValue
    private Long id;

    private List<String> relations = new ArrayList<>();

    private Knowledge pre;

    private Knowledge pos;

    public Relation(){}

    public Relation(Knowledge pre,Knowledge pos){
        this.pre = pre;
        this.pos = pos;
    }

    public void addRoleName(String name) {
        if (this.relations == null) {
            this.relations = new ArrayList<>();
        }
        this.relations.add(name);
    }


}
