package pl.exam.app.jsf.beans.exam.timer;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.Serializable;

@Component
public class AsyncTimer implements Serializable {
    private final static long UPDATE_INTERVAL_MILLISECONDS = 5000;

    @Async
    public void startTimer(double availableTime, TimerObserver observer) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        long secondsElapsed = 0;
        while (availableTime > secondsElapsed) {
            secondsElapsed = (long)(stopWatch.getLastTaskTimeMillis()/1000.0f);
            observer.update(secondsElapsed);
            trySleeping(UPDATE_INTERVAL_MILLISECONDS);
        }
        stopWatch.stop();
        observer.update(secondsElapsed);
    }

    private void trySleeping(long updateInterval) {
        try {
            Thread.sleep(updateInterval);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
