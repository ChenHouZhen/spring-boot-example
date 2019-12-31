package com.chenhz.blog.controller;

import com.chenhz.blog.form.UserForm;
import com.chenhz.common.entity.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Slf4j
@RestController
@Api(tags = "校验中心")
@Validated
public class SysValidController {

    @PostMapping(value = "/sys/valid/user")
    @ApiOperation(value = "验证用户对象")
    public R validUser(@RequestBody @Validated UserForm form) {
        log.info(">>>>> 用户信息：{}",form);
        return R.ok().put("data",form);
    }

    @PostMapping(value = "/sys/valid/user/2")
    @ApiOperation(value = "验证用户对象")
    public R validUser2(@Validated UserForm form) {
        log.info(">>>>> 用户信息：{}",form); 
        return R.ok().put("data",form);
    }

    @PostMapping(value = "/sys/valid/user/3")
    @ApiOperation(value = "验证用户对象")
    public R validUser3(@Valid @NotBlank(message = "name 不能为空") String name, Integer age) {
        log.info(">>>>> 用户信息：{},{}",name,age);
        return R.ok().put("data",name);
    }
}
