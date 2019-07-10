package com.chenhz.blog.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    AbstractValidateCodeProcessor abstractValidateCodeProcessor;

    /**
     * 退出
     */
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String logout() {
        abstractValidateCodeProcessor.generate();
        return "";
    }

}
