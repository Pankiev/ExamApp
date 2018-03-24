package pl.exam.app.jsf.beans.exam.timer;

import com.google.common.base.Stopwatch;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@SessionScoped
@Component
public class AsyncTimer implements Serializable
{
	private final static long UPDATE_INTERVAL_MILLISECONDS = 5000;

	@Async
	public void startTimer(double availableTime, TimerObserver observer)
	{
		Stopwatch stopWatch = Stopwatch.createStarted();
		long secondsElapsed = 0;
		while(availableTime > secondsElapsed)
		{
			secondsElapsed = stopWatch.elapsed(TimeUnit.SECONDS);
			observer.update(secondsElapsed);
			trySleeping(UPDATE_INTERVAL_MILLISECONDS);
		}
		stopWatch.stop();
		observer.update(secondsElapsed);
	}

	private void trySleeping(long updateInterval)
	{
		try
		{
			Thread.sleep(updateInterval);
		} catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}
}
