package le.hieu.scheduler;

import jakarta.annotation.PostConstruct;
import le.hieu.scheduler.job.HelloWorldJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuartzHelloWorldScheduler {
    private final Scheduler scheduler;

    @PostConstruct
    public void scheduleHelloWorldJob() throws SchedulerException {
        JobDetail jobDetail = buildJobDetail();
        Trigger trigger = buildTrigger();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    public void scheduleJobWithCron(String cronExpression) throws SchedulerException {
        JobDetail jobDetail = buildCronJobDetail();
        Trigger trigger = buildCronJobTrigger(cronExpression);

        scheduler.scheduleJob(jobDetail, trigger);
    }

    private static Trigger buildCronJobTrigger(String cronExpression) {
        return TriggerBuilder.newTrigger()
                .withIdentity("cronTrigger_" + System.currentTimeMillis(), "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    }

    private static JobDetail buildCronJobDetail() {
        return JobBuilder.newJob(HelloWorldJob.class)
                .withIdentity("cronJob_" + System.currentTimeMillis(), "group1")
                .build();
    }

    private static Trigger buildTrigger() {
        return TriggerBuilder.newTrigger()
                            .withIdentity("helloWorldTrigger", "group1")
                            .startNow()
                            .withSchedule(
                                SimpleScheduleBuilder.simpleSchedule()
                                    .withIntervalInSeconds(1)
                                    .repeatForever())
                            .build();
    }

    private static JobDetail buildJobDetail() {
        return JobBuilder.newJob(HelloWorldJob.class)
                        .withIdentity("helloWorldJob", "group1")
                        .build();
    }
}
