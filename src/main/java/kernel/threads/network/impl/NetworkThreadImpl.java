package kernel.threads.network.impl;

import com.google.common.util.concurrent.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import gui.FXPanels;
import javafx.application.*;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import kernel.threads.network.NetworkThread;
import org.javatuples.Pair;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by kadir.basol on 25.3.2016.
 */
@Singleton
public class NetworkThreadImpl implements NetworkThread {

    private ListeningExecutorService  networkThread;
    private Parent                    waitParent;

    public NetworkThreadImpl() {
        networkThread = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1));
    }



    @Inject
    @Named("MainBorder")
    private BorderPane borderPane;


    @Inject
    private FXPanels fxPanels;




    @Override
    public void aSyncOperation( StackPane actionPanel , Callable<Object> callable ,  FutureCallback<Object> futureCallback ) {
        final Pair<Pane, Object> waıt = fxPanels.getByName("WAIT");
        actionPanel.getChildren().add( waıt.getValue0() );
        ListenableFuture<Object> submit = networkThread.submit(callable);

        FutureCallback futurePre = new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object o) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        actionPanel.getChildren().remove(waıt.getValue0());
                    }
                });
                futureCallback.onSuccess(o);
//                stackpane wait pop
            }

            @Override
            public void onFailure(Throwable throwable) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        actionPanel.getChildren().remove(waıt.getValue0());
                    }
                });
                futureCallback.onFailure(throwable);
//                stackpane wait pop
            }
        };
        Futures.addCallback(submit, futurePre );
    }


    @Override
    public void registerWaitPanel(Parent pane) {
        this.waitParent = pane;
    }



}
