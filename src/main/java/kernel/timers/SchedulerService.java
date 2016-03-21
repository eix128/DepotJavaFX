package kernel.timers;

import com.google.inject.Singleton;

/**
 * Created by kadir.basol on 16.3.2016.
 */
@Singleton
public interface SchedulerService {
    public void doOperation( Runnable runnable ,  long timeLaterMillis );
}
