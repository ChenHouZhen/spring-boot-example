package com.chenhz.neo4j.domain;

import com.chenhz.neo4j.pojo.RelationshipTypes;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

@Getter
@Setter
@RelationshipEntity(type = RelationshipTypes.R_IN)
public class KnowledgeRelation {

    // @GraphId 在 Boot 2.0 后不在支持
    @Id
    @GeneratedValue
    private Long id;

    //@StartNode  @EndNode 不加这两个 ，启动会报错：org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'neo4jAuditionBeanFactoryPostProcessor': Unsatisfied dependency expressed through constructor parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'sessionFactory' defined in class path resource [com/zj/dataserver/ApplicationConfiguration.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.neo4j.ogm.session.SessionFactory]: Factory method 'sessionFactory' threw exception; nested exception is org.neo4j.ogm.exception.core.ConfigurationException: Could not load driver class org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver

    @StartNode
    private Knowledge pre;

    @EndNode
    private Knowledge pos;

    private Integer type;

    public KnowledgeRelation(){}

    public KnowledgeRelation(Knowledge pre, Knowledge pos, Integer type){
        this.pre = pre;
        this.type = type;
        this.pos = pos;
    }

    public KnowledgeRelation(Knowledge pre, Knowledge pos){
        this.pre = pre;
        this.type = 1;
        this.pos = pos;
    }


    public Integer getType() {
        return type;
    }

    public Knowledge getPos() {
        return pos;
    }

    public Knowledge getPre() {
        return pre;
    }

}
