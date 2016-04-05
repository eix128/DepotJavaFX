package main;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.application.Application;
import javafx.stage.Stage;
import kernel.events.ConnectionException;
import kernel.network.DBManager;

/**
 * Created by kadir.basol on 24.3.2016.
 */
@Singleton
public class MainAppImpl extends Application implements MainApp {

    @Inject
    private DBManager dbManager;



    @Override
    public void init2() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //        Login olmaya çalış , başarısız olursa login panel açılsın
        try {
        //            E:\SyncProjects\DepoProje\src\main\resources\natives\win64
            dbManager.connectNTLM();
        } catch (ConnectionException e) {

        }
        //        Başarılı olursa depo malları görüntülenen panel açılsın

    }
}
