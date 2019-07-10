package com.chenhz.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    // 没找到这个包
//    @Indexed(indexType = IndexType.FULLTEXT, indexName = "userName")
    private String title;


    //private List<Property> properties;

    //private Property property;
    @JsonIgnore
//    @JsonIgnoreProperties("knowledge") // 这行是什么作用?
    // direction = Relationship.UNDIRECTED 为啥没作用
    @Relationship(type = "R_IN")
    private List<KnowledgeRelation> relations;


/*    @JsonIgnoreProperties("models")
    @Relationship(type = "ACTED_IN", direction = Relationship.OUTGOING)*/
    //private List<Knowledge> children;


    public Knowledge(){
//        if (children == null){
//            this.children = new ArrayList<>();
//        }
        if (relations == null){
            this.relations = new ArrayList<>();
        }
    }

    public Knowledge(String title){
        this();
        this.title = title;
    }


    public void addChind(Knowledge k){
        KnowledgeRelation r = new KnowledgeRelation(this,k);
      //  this.children.add(k);
        this.relations.add(r);
    }

//    public List<KnowledgeRelation> getRelations() {
//        return this.relations;
//    }
//
//    public void addRelation(KnowledgeRelation relation) {
//        if (this.relations == null) {
//            this.relations = new ArrayList<>();
//        }
//        this.relations.add(relation);
//    }

}
