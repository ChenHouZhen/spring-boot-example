package com.chenhz.neo4j.utils.validation;

import org.apache.commons.lang.StringUtils;

public class NullValidation implements Validation {


    @Override
    public boolean check(String text) {
        return StringUtils.isNotBlank(text);
    }

    @Override
    public String msg() {
        return "不允许为空";
    }


}
