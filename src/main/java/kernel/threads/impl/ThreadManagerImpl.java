package kernel.threads.impl;

import com.google.inject.Singleton;
import kernel.threads.ThreadManager;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Kadir on 3/24/2016.
 */
@Singleton
public class ThreadManagerImpl implements ThreadManager {

    private ExecutorService executors;


    @Override
    public void doAction(Runnable runnable) {

    }

//    @Override
    public Future<?> doActionAsync(Callable<?> runnable) {
        return executors.submit(runnable);
    }

}
