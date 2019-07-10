package com.chenhz.blog.test;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码生成器
 *
 * @author paascloud.net @gmail.com
 */
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {


	@Override
	public void generate(ServletWebRequest request) {
		System.out.println(">>>> SmsCodeGenerator");
	}
}
