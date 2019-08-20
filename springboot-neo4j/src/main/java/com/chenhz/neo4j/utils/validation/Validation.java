package com.chenhz.neo4j.utils.validation;

public interface Validation {

    boolean check(String text);

    String msg();
}
