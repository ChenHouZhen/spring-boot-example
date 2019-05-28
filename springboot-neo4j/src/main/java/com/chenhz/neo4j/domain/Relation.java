package com.chenhz.neo4j.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;

@Data
@RelationshipEntity(type = "ACTED_IN")
public class Relation {

    @Id
    @GeneratedValue
    private Long id;

    private Knowledge pre;

    private Knowledge pos;

    private Integer type;

    public Relation(){}

    public Relation(Knowledge pre,Knowledge pos,Integer type){
        this.pre = pre;
        this.type = type;
        this.pos = pos;
    }

    public Relation(Knowledge pre,Knowledge pos){
        this.pre = pre;
        this.type = 1;
        this.pos = pos;
    }

}
