package com.chenhz.neo4j.utils;

import com.chenhz.neo4j.domain.DynamicNode;

public class DynamicNodePropertiesConverter extends DynamicPropertiesConverter{

    public DynamicNodePropertiesConverter() {
        super(DynamicNode.class);
    }
}
