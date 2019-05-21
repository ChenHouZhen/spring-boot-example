package com.chenhz.neo4j.domain;

import com.chenhz.neo4j.utils.DynamicNodePropertiesConverter;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.util.Map;

@NodeEntity
@Data
public class DynamicNode {
    @Id
    @GeneratedValue
    private Long id;
    private String title;

    public DynamicNode(String title){
        this.title = title;
    }

    public DynamicNode(String title,Map<String, Object> data){
        this.title = title;
        this.data = data;
    }


    @Convert(DynamicNodePropertiesConverter.class)
    public Map<String, Object> data;
}
