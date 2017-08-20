package com.speedcheck.job;

import com.speedcheck.service.SpeedTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpeedTestJob {

    private final SpeedTestService speedTestService;

    @Autowired
    public SpeedTestJob(final SpeedTestService speedTestService) {
        this.speedTestService = speedTestService;
    }

    //    @Scheduled(cron = "0 * * * * *")
    public void runScheduledTest() throws InterruptedException {
        speedTestService.test(SpeedTestService.TEST_TYPE.BOTH);
    }


}
