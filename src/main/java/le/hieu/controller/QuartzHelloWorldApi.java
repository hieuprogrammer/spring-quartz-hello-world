package le.hieu.controller;

import le.hieu.scheduler.QuartzHelloWorldScheduler;
import le.hieu.scheduler.dto.CronExpressionRequest;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class QuartzHelloWorldApi {
    private final QuartzHelloWorldScheduler quartzHelloWorldScheduler;

    @GetMapping("/hello")
    public String sayHelloWorld() throws SchedulerException {
        quartzHelloWorldScheduler.scheduleHelloWorldJob();
        return "Trigger is scheduled. Check console!";
    }

    @PostMapping("/hello/schedule")
    public String scheduleCronJob(@RequestParam String cron) throws SchedulerException {
        quartzHelloWorldScheduler.scheduleJobWithCron(cron);
        return "Job scheduled with cron: " + cron;
    }

    @PostMapping("/hello/schedule")
    public String scheduleCronJob(@RequestBody CronExpressionRequest request) throws SchedulerException {
        quartzHelloWorldScheduler.scheduleJobWithCron(request.getCronExpression());
        return "Job scheduled with cron expression: " + request.getCronExpression();
    }
}
