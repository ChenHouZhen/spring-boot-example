package com.chenhz.multi.sender;

import com.chenhz.common.entity.R;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@Component
public class OneSender {


    @Resource(name = "oneRabbitTemplate")
    private RabbitTemplate rabbitTemplate;


    @PostMapping("/send/1")
    public R send(){
        String context = "HELLO-1 : " + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello-1", context);
        return R.ok().put("data",context);
    }
}
