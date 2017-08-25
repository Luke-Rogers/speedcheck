package com.speedcheck.service;

import com.speedcheck.domain.Result;
import com.speedcheck.repository.ResultRepository;
import com.speedcheck.transfer.Notification;
import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class SpeedTestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedTestService.class);
    private static final double C_MBIT = Math.pow(10d, 6d);
    private static final String DOWNLOAD_TEST_URL = "http://2.testdebit.info/fichiers/100Mo.dat";
    private static final String UPLOAD_TEST_URL = "http://2.testdebit.info/";

    private final ResultRepository resultRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public SpeedTestService(final ResultRepository resultRepository, final SimpMessagingTemplate messagingTemplate) {
        this.resultRepository = resultRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void test(TEST_TYPE testType) throws InterruptedException {
        final Timestamp timestamp = new Timestamp(Instant.now().toEpochMilli());
        if (TEST_TYPE.BOTH.equals(testType) || TEST_TYPE.DOWNLOAD.equals(testType)) {
            downloadTest(timestamp);
        }
        if (TEST_TYPE.BOTH.equals(testType) || TEST_TYPE.UPLOAD.equals(testType)) {
            uploadTest(timestamp);
        }
    }

    private void downloadTest(final Timestamp timestamp) throws InterruptedException {
        LOGGER.info("STARTING DOWNLOAD TEST");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        SpeedTestSocket speedTestSocket = new SpeedTestSocket(500);
        speedTestSocket.addSpeedTestListener(getSpeedTestListener(Result.TYPE.DOWNLOAD, timestamp, countDownLatch));
        speedTestSocket.startFixedDownload(DOWNLOAD_TEST_URL, 10000);
        countDownLatch.await(30, TimeUnit.SECONDS);
    }

    private void uploadTest(final Timestamp timestamp) throws InterruptedException {
        LOGGER.info("STARTING UPLOAD TEST");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        SpeedTestSocket speedTestSocket = new SpeedTestSocket(500);
        speedTestSocket.addSpeedTestListener(getSpeedTestListener(Result.TYPE.UPLOAD, timestamp, countDownLatch));
        speedTestSocket.startFixedUpload(UPLOAD_TEST_URL, 10000000, 10000);
        countDownLatch.await(30, TimeUnit.SECONDS);
    }

    private final ISpeedTestListener getSpeedTestListener(final Result.TYPE type, final Timestamp timestamp, final CountDownLatch countDownLatch) {

        final Result result = new Result(type, timestamp);

        return new ISpeedTestListener() {

            @Override
            public void onCompletion(SpeedTestReport report) {
                double mBits = bitsToMegaBits(report.getTransferRateBit().doubleValue());
                result.setSpeed(mBits);
                resultRepository.save(result);
                countDownLatch.countDown();
                sendSocketNotification(type, result.getSpeed());
                LOGGER.info("FINISHED " + type + " TEST : " + result.getSpeed());
            }

            @Override
            public void onError(SpeedTestError speedTestError, String errorMessage) {
                result.setStatus(false);
                resultRepository.save(result);
                countDownLatch.countDown();
                LOGGER.info("FINISHED " + type + " TEST : " + result.getSpeed());
            }

            @Override
            public void onProgress(float percent, SpeedTestReport report) {
                result.setSpeed(bitsToMegaBits(report.getTransferRateBit().doubleValue()));
            }

            @Override
            public void onInterruption() {
                resultRepository.save(result);
                countDownLatch.countDown();
                sendSocketNotification(type, result.getSpeed());
                LOGGER.info("FINISHED " + type + " TEST : " + result.getSpeed());
            }

        };

    }

    private void sendSocketNotification(final Result.TYPE testType, final double speed) {
        messagingTemplate.convertAndSend("/topic/speedtest", new Notification(testType, speed));
    }

    private double bitsToMegaBits(double bits) {
        return bits / C_MBIT;
    }

    public enum TEST_TYPE {
        BOTH, DOWNLOAD, UPLOAD
    }

}
