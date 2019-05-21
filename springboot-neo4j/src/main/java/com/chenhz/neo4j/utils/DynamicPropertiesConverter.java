package com.chenhz.neo4j.utils;

import org.neo4j.ogm.typeconversion.CompositeAttributeConverter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class DynamicPropertiesConverter implements CompositeAttributeConverter<Map<String,?>> {

    private Set<String> blacklist;

    public DynamicPropertiesConverter(Class<?> clazz){
        blacklist = new HashSet<>();
        addAllFields(clazz);
    }

    public DynamicPropertiesConverter(Set<String> blackList){
        this.blacklist = blackList;
    }

    public void addAllFields(Class<?> type){
        for (Field field:type.getDeclaredFields()){
            blacklist.add(field.getName());
        }

        if (type.getSuperclass() != null){
            addAllFields(type.getSuperclass());
        }
    }

    @Override
    public Map<String, ?> toGraphProperties(Map<String, ?> stringMap) {
        Map<String,?> result = new HashMap<>(stringMap);
        result.keySet().removeAll(blacklist);
        return result;
    }

    @Override
    public Map<String, ?> toEntityAttribute(Map<String, ?> map) {
        return toGraphProperties(map);
    }
}
