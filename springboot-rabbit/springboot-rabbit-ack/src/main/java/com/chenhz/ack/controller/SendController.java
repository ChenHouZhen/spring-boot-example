package com.chenhz.ack.controller;

import com.chenhz.ack.ack.RabbitAckSender;
import com.chenhz.common.entity.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("send")
@Api(tags = "发送消息队列")
public class SendController {

    @Autowired
    RabbitAckSender ackSender;

    @PostMapping("/ack/a")
    public R sendACK(String message){
        ackSender.sendMessage(message);
        return R.ok();
    }

}
