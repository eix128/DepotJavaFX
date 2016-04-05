package kernel.threads.network;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.util.concurrent.Callable;

/**
 * Created by kadir.basol on 25.3.2016.
 */
public interface NetworkThread {
    public void aSyncOperation( StackPane actionPanel , Callable<Object> callable ,  FutureCallback<Object> futureCallback );
    public void registerWaitPanel(Parent pane);
}
