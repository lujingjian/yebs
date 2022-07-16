package com.example.server.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <  消息类>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMsg {

    @ApiModelProperty(value = "谁发给我的")
    private String from;

    @ApiModelProperty(value = "发到哪里去")
    private String to;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "时间")
    private LocalDateTime date;

    @ApiModelProperty(value = "昵称")
    private String forNickName;




}
