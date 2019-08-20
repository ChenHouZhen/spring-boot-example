package com.chenhz.neo4j.utils.validation;

public class SymbolValidation implements Validation {


    @Override
    public boolean check(String text) {
        return text != null &&  (text.contains(",") || text.contains("-") || text.contains("，"));
    }

    @Override
    public String msg() {
        return "不允许出现字符：- ，,";
    }
}
