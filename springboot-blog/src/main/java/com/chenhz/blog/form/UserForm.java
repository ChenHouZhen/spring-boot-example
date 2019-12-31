package com.chenhz.blog.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
@ApiModel("用户信息")
public class UserForm {

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    @Length(min = 4,max = 10,message = "用户名称长度需要在 4 和 10 之间")
    private String name;

    /**
     * https://blog.csdn.net/lilyssh/article/details/82944507
     *
     * Swagger2 打开页面报异常:java.lang.NumberFormatException:For input string:""
     *
     * 解决办法：实体类中，Integer类型的属性加@ApiModelProperty时，必须要给example参数赋值，且值必须为数字类型。
     */
    @ApiModelProperty(value = "年龄",example = "1")
    @NotNull(message = "年龄不能为空")
    @Max(value = 100)
    @Min(value = 0)
    private Integer age;

    @ApiModelProperty("邮箱")
    @Email(message = "邮箱不合法")
    private String email;

    @ApiModelProperty("手机号")
    @Pattern(regexp = "^[1](([3|5|8][\\\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\\\d]{8}$",message = "手机号不合法")
    private String phone;

}
