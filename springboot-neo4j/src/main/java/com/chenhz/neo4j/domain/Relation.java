package com.chenhz.neo4j.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

@Data
@RelationshipEntity(type = "R_IN")
public class Relation {

    @Id
    @GeneratedValue
    private Long id;

    //为了解决对象中存在双向引用导致的无限递归（infinite recursion）问题
    // 有这个会报错 StackOverflowError
    @JsonBackReference
//    @JSONField(serialize = false)
    @StartNode //@StartNode  @EndNode 不加这两个 ，启动会报错：org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'neo4jAuditionBeanFactoryPostProcessor': Unsatisfied dependency expressed through constructor parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'sessionFactory' defined in class path resource [com/zj/dataserver/ApplicationConfiguration.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.neo4j.ogm.session.SessionFactory]: Factory method 'sessionFactory' threw exception; nested exception is org.neo4j.ogm.exception.core.ConfigurationException: Could not load driver class org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver
    private Knowledge pre;

//    @JsonIgnore
//    @JSONField(serialize = false)
    @JsonBackReference
    @EndNode
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


    public Integer getType() {
        return type;
    }

/*    public Knowledge getPos() {
        return pos;
    }

    public Knowledge getPre() {
        return pre;
    }*/

}
