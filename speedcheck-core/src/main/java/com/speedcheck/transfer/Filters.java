package com.speedcheck.transfer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import static com.speedcheck.service.SpeedTestService.TEST_TYPE;

public class Filters {

    private final TEST_TYPE type;
    private final Date fromDate;
    private final Date toDate;

    @JsonCreator
    public Filters(@JsonProperty("type") TEST_TYPE type, @JsonProperty("fromDate") Date fromDate, @JsonProperty("toDate") Date toDate) {
        this.type = type;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public TEST_TYPE getType() {
        return type;
    }
}
