package com.chenhz.blog.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("用户信息")
public class UserForm {

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String name;


    @ApiModelProperty("年龄")
    @NotNull(message = "年龄不能为空")
    private Integer age;

}
