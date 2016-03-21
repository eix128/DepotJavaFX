package kernel.timers.impl;

import com.google.inject.Singleton;
import kernel.timers.SchedulerService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kadir.basol on 16.3.2016.
 */
@Singleton
public class SchedulerServiceImpl implements SchedulerService {

    private ScheduledExecutorService executorService;

    public SchedulerServiceImpl() {
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void doOperation(Runnable runnable,long timeLaterMillis) {
        executorService.schedule(runnable,timeLaterMillis, TimeUnit.MILLISECONDS);
    }
}
