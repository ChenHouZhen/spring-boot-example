package com.chenhz.blog.controller;

import com.chenhz.blog.form.UserForm;
import com.chenhz.common.entity.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "验证中心")
public class SysValidController {

    @PostMapping(value = "/sys/valid/user")
    @ApiOperation(value = "验证用户对象")
    public R validUser(@RequestBody @Validated UserForm form) {
        log.info(">>>>> 用户信息：{}",form);
        return R.ok().put("data",form);
    }
}
