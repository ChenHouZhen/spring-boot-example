package com.chenhz.quartz.quartz.scheduler;

import com.chenhz.quartz.quartz.job.PrintJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

public class PrintScheduler {

    public static void main(String[] args) throws SchedulerException, InterruptedException {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(PrintJob.class).withIdentity("job1","group1").build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1","triggerGroup1").startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(1)
                .repeatForever()
               ).build();

        // 绑定
        scheduler.scheduleJob(jobDetail,trigger);
        System.out.println(">>>>> scheduler start");
        // 执行
        scheduler.start();


        TimeUnit.MINUTES.sleep(1);
        // 停止
        scheduler.shutdown();
        System.out.println(">>>>> scheduler shutdown");
    }
}
