package com.example.server.pojo;
/*接收前端参数的类 只判断账户密码就够 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 登录实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "adminLogin对象",description = "")
public class AdminLoginParam {

    @ApiModelProperty(value = "用户名",required =true)//required = true必须填的
    private String username;

    @ApiModelProperty(value = "密码",required =true)
    private String password;
    //添加验证码登录实体
    @ApiModelProperty(value ="验证码",required = true)
    private String code; //然后去找登录controller添加图片

}
