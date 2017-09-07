package com.speedcheck.transfer;

import com.speedcheck.domain.Result;

import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.stream.Collectors;

public class Results {

    private final Collection<TestResult> results;
    private final double averageDownload;
    private final double averageUpload;
    private final double maxDownload;
    private final double maxUpload;

    public Results(Collection<Result> results) {
        this.results = results.stream().map(result -> TestResult.from(result)).collect(Collectors.toList());

        if (results.stream().anyMatch(result -> Result.TYPE.DOWNLOAD.equals(result.getType()))) {
            final DoubleSummaryStatistics downloadStatistics = getStatistics(Result.TYPE.DOWNLOAD);
            averageDownload = downloadStatistics.getAverage();
            maxDownload = downloadStatistics.getMax();
        } else {
            averageDownload = 0.0;
            maxDownload = 0.0;
        }

        if (results.stream().anyMatch(result -> Result.TYPE.UPLOAD.equals(result.getType()))) {
            final DoubleSummaryStatistics uploadStatistics = getStatistics(Result.TYPE.UPLOAD);
            averageUpload = uploadStatistics.getAverage();
            maxUpload = uploadStatistics.getMax();
        } else {
            averageUpload = 0.0;
            maxUpload = 0.0;
        }

    }

    private DoubleSummaryStatistics getStatistics(Result.TYPE type) {
        return results
                .stream()
                .filter(result -> result.getType().equals(type) && result.isStatus())
                .mapToDouble(TestResult::getSpeed)
                .summaryStatistics();
    }

    public Collection<TestResult> getResults() {
        return results;
    }

    public double getAverageDownload() {
        return averageDownload;
    }

    public double getAverageUpload() {
        return averageUpload;
    }

    public double getMaxDownload() {
        return maxDownload;
    }

    public double getMaxUpload() {
        return maxUpload;
    }
}
