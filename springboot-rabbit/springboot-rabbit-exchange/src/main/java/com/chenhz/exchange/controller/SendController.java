package com.chenhz.exchange.controller;

import com.chenhz.common.entity.R;
import com.chenhz.exchange.direct.RabbitDirectSender;
import com.chenhz.exchange.fanout.RabbitFanoutSender;
import com.chenhz.exchange.topic.RabbitTopicSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("send")
@Api(tags = "发送消息队列")
public class SendController {

    @Autowired
    RabbitDirectSender directSender;

    @Autowired
    RabbitFanoutSender fanoutSender;

    @Autowired
    RabbitTopicSender topicSender;


    @PostMapping("/direct/h")
    @ApiOperation("directExchange")
    public R sendHello(){
        directSender.sendHello();
        return R.ok();
    }

    @PostMapping("/direct/s")
    public R sendDirect(){
        directSender.sendDirect();
        return R.ok();
    }

    @PostMapping("/fanout/p")
    public R sendPenglei(){
        fanoutSender.sendPenglei();
        return R.ok();
    }

    @PostMapping("/fanout/s")
    public R sendSouyunku(){
        fanoutSender.sendSouyunku();
        return R.ok();
    }

    @PostMapping("/topic/m")
    public R sendMessage(){
        topicSender.sendMessage();
        return R.ok();
    }

    @PostMapping("/topic/ms")
    public R sendMessages(){
        topicSender.sendMessages();
        return R.ok();
    }
    @PostMapping("/topic/y")
    public R sendYmq(){
        topicSender.sendYmq();
        return R.ok();
    }

}
