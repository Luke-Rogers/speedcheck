package com.speedcheck.transfer;

import java.util.Date;

public class TestResult {
    private final int id;
    private final Date timestamp;
    private final boolean status;
    private final com.speedcheck.domain.Result.TYPE type;
    private final double speed;

    public TestResult(int id, Date timestamp, boolean status, com.speedcheck.domain.Result.TYPE type, double speed) {
        this.id = id;
        this.timestamp = timestamp;
        this.status = status;
        this.type = type;
        this.speed = speed;
    }

    public static TestResult from(com.speedcheck.domain.Result result) {
        return new TestResult(result.getId(), result.getTimestamp(), result.isStatus(), result.getType(), result.getSpeed());
    }

    public int getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public boolean isStatus() {
        return status;
    }

    public com.speedcheck.domain.Result.TYPE getType() {
        return type;
    }

    public double getSpeed() {
        return speed;
    }
}
