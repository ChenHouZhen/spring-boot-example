package com.chenhz.quartz.spring;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@EnableScheduling
@Component
public class SpringScheduler {

    // 每隔 5 秒执行

    @Scheduled(cron = "0/5 * *  * * ? ")
    public void printHelloWord(){
        String printTime = new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new Date());
        System.out.println("PrintWordsJob start at:" + printTime + ", prints: Hello Job-" + new Random().nextInt(100));
    }
}
