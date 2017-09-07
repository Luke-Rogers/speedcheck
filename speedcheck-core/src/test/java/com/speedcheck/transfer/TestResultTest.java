package com.speedcheck.transfer;

import com.speedcheck.domain.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TestResultTest {

    @Test
    public void should_get_test_result_from_result() {
        Result result = new Result();
        result.setSpeed(10.0);
        result.setStatus(true);
        result.setId(1);
        Timestamp timestamp = new Timestamp(System.nanoTime());
        result.setTimestamp(timestamp);
        result.setType(Result.TYPE.DOWNLOAD);

        TestResult testResult = TestResult.from(result);
        assertThat(testResult.getSpeed(), equalTo(10.0));
        assertTrue(testResult.isStatus());
        assertThat(testResult.getId(), equalTo(1));
        assertThat(testResult.getType(), equalTo(Result.TYPE.DOWNLOAD));
        assertThat(testResult.getTimestamp(), equalTo(timestamp));
    }


}
