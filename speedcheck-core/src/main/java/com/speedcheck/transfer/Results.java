package com.speedcheck.transfer;

import com.speedcheck.domain.Result;

import java.util.Collection;
import java.util.DoubleSummaryStatistics;

public class Results {

    private final Collection<Result> results;
    private final double averageDownload;
    private final double averageUpload;
    private final double maxDownload;
    private final double maxUpload;

    public Results(Collection<Result> results) {
        this.results = results;

        final DoubleSummaryStatistics downloadStatistics = getStatistics(Result.TYPE.DOWNLOAD);
        averageDownload = downloadStatistics.getAverage();
        maxDownload = downloadStatistics.getMax();

        final DoubleSummaryStatistics uploadStatistics = getStatistics(Result.TYPE.UPLOAD);
        averageUpload = uploadStatistics.getAverage();
        maxUpload = uploadStatistics.getMax();
    }

    private DoubleSummaryStatistics getStatistics(Result.TYPE type) {
        return results
                .stream()
                .filter(result -> result.getType().equals(type))
                .mapToDouble(Result::getSpeed)
                .summaryStatistics();
    }

    public Collection<Result> getResults() {
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
