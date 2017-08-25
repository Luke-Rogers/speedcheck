package com.speedcheck.controller;

import com.speedcheck.service.SpeedTestService;
import com.speedcheck.service.SpeedTestService.TEST_TYPE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/test")
public class SpeedTestController {

    private final SpeedTestService speedTestService;

    @Autowired
    public SpeedTestController(final SpeedTestService speedTestService) {
        this.speedTestService = speedTestService;
    }

    @RequestMapping
    public void runTest(@RequestParam(name = "type") Optional<TEST_TYPE> type) throws InterruptedException {
        speedTestService.test(type.orElse(TEST_TYPE.BOTH));
    }

}
