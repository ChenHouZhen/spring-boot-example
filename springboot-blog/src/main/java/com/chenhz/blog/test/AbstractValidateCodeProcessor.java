/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AbstractValidateCodeProcessor.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.chenhz.blog.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

@Component
@Slf4j
public  class AbstractValidateCodeProcessor {

	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 */
	private final Map<String, ValidateCodeGenerator> validateCodeGenerators;


	@Autowired
	protected AbstractValidateCodeProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators) {
	    log.info(">>>> 初始化 AbstractValidateCodeProcessor");
	    log.info(">>>> 参数如下");
	    log.info(validateCodeGenerators.toString());
		this.validateCodeGenerators = validateCodeGenerators;
	}

    public void generate() {
        System.out.println(validateCodeGenerators);
    }

}
