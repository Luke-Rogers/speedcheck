package com.speedcheck.transfer;

import com.speedcheck.domain.Result;

public class Notification {

    private final Result.TYPE testType;
    private final double speed;

    public Notification(Result.TYPE testType, double speed) {
        this.testType = testType;
        this.speed = speed;
    }

    public Result.TYPE getTestType() {
        return testType;
    }

    public double getSpeed() {
        return speed;
    }
}
