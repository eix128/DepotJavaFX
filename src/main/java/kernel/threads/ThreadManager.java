package kernel.threads;

import com.google.inject.Singleton;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by kadir.basol on 16.3.2016.
 */
public interface ThreadManager {
    public void doAction(Runnable runnable);
    public Future<?> doActionAsync(Callable<?> runnable);
}
