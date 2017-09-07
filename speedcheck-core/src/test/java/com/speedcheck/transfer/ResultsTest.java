package com.speedcheck.transfer;

import com.speedcheck.domain.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ResultsTest {

    @Test
    public void should_construct_results() {
        Collection<Result> savedResults = Arrays.asList(
                buildResult(10.0, Result.TYPE.DOWNLOAD, true),
                buildResult(15.0, Result.TYPE.DOWNLOAD, true),
                buildResult(25.0, Result.TYPE.DOWNLOAD, false),
                buildResult(10.0, Result.TYPE.UPLOAD, true),
                buildResult(15.0, Result.TYPE.UPLOAD, true),
                buildResult(25.0, Result.TYPE.UPLOAD, false));

        Results results = new Results(savedResults);

        assertThat(results.getResults().size(), equalTo(6));
        assertThat(results.getAverageDownload(), equalTo(12.5));
        assertThat(results.getMaxDownload(), equalTo(15.0));
        assertThat(results.getAverageUpload(), equalTo(12.5));
        assertThat(results.getMaxUpload(), equalTo(15.0));
    }

    @Test
    public void should_default_statistics_when_none_of_type() {
        Results results = new Results(Collections.emptyList());
        assertThat(results.getResults().size(), equalTo(0));
        assertThat(results.getAverageDownload(), equalTo(0.0));
        assertThat(results.getMaxDownload(), equalTo(0.0));
        assertThat(results.getAverageUpload(), equalTo(0.0));
        assertThat(results.getMaxUpload(), equalTo(0.0));
    }

    private Result buildResult(double speed, Result.TYPE type, boolean status) {
        Result result = new Result();
        result.setSpeed(speed);
        result.setStatus(status);
        result.setId(1);
        Timestamp timestamp = new Timestamp(System.nanoTime());
        result.setTimestamp(timestamp);
        result.setType(type);
        return result;
    }

}
