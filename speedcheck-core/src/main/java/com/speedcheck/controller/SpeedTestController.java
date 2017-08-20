package com.speedcheck.controller;

import com.speedcheck.service.SpeedTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class SpeedTestController {

    private final SpeedTestService speedTestService;

    @Autowired
    public SpeedTestController(final SpeedTestService speedTestService) {
        this.speedTestService = speedTestService;
    }

    @RequestMapping
    public void runTest(@RequestParam(name = "type", required = false) String type) throws InterruptedException {
        SpeedTestService.TEST_TYPE testType = type != null ? SpeedTestService.TEST_TYPE.valueOf(type) : SpeedTestService.TEST_TYPE.BOTH;
        speedTestService.test(testType);
    }

}
