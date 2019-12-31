package com.chenhz.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString(exclude = "relations")
@Getter
@Setter
@NodeEntity(label = "Knowledge")
public class Knowledge {

    @Id
    @GeneratedValue
    private Long id;

    private String title;


    /**
     * 测试，Date 的时间
     *
     * 结论：Neo4j 存储 Date 的时间格式不正确，存在时区问题
     *
     * 解决办法： 将日期类型转换为时间戳，或者用 @DateString 转换为String
     *           或者直接存储 Long 类型的时间
     *
     */

    @DateLong
    private Date createTime;

    @JsonIgnore
    private String name;


    private Long updateTime;

    @JsonIgnore
    @Relationship(type = "R_IN")
    private List<KnowledgeRelation> relations;

    public Knowledge(){
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
        this.relations.add(r);
    }


}
