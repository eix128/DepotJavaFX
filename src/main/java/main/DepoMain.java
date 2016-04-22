package main;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import globals.GlobalDatas;
import gui.FXPanels;
import gui.PanelNames;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.BuilderFactory;
import javafx.util.Callback;
import jpa.converters.enums.ProcessType;
import kernel.network.DBManager;
import kernel.threads.network.NetworkThread;
import main.gui.GuiceControllerFactory;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import utils.ThermalPrinter;
import utils.natives.NativeUtilities;

import javax.print.PrintException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by kadir.basol on 19.2.2016.
 */
public class DepoMain extends Application {

    private FXPanels fxPanels;

    private DBManager dbManager;
    private Stage     primaryStage;


    public DepoMain() throws IOException {

    }


    public void setupPanels(Injector injector, AgentFinderModule agentFinderModule, BuilderFactory builderFactory, Callback<Class<?>, Object> guiceControllerFactory) throws IOException {

        /*
        FXMLLoader loginViewLoader = new FXMLLoader(agentFinderModule.getClass().getResource("/login.fxml"), null, builderFactory, guiceControllerFactory);
        Pane loginView = (Pane) loginViewLoader.load();
        LoginController controller1 = loginViewLoader.getController();

        FXMLLoader mainViewLoader = new FXMLLoader(agentFinderModule.getClass().getResource("/mainpanel.fxml"), null, builderFactory, guiceControllerFactory);
        Pane mainView = (Pane) mainViewLoader.load();
        MainPanelController mainViewLoaderController = mainViewLoader.getController();
        final MainPanelController controller = mainViewLoader.getController();

        FXMLLoader depoViewLoader = new FXMLLoader(agentFinderModule.getClass().getResource("/depoStokEkrani.fxml"), null, builderFactory, guiceControllerFactory);
        Pane stockView = (Pane) depoViewLoader.load();
        final DepotsController depoViewLoaderController = depoViewLoader.getController();


        FXMLLoader productInViewLoader = new FXMLLoader(agentFinderModule.getClass().getResource("/productInput.fxml"), null, builderFactory, guiceControllerFactory);
        Pane productInView = (Pane) productInViewLoader.load();
        final ProductInputController controller3 = productInViewLoader.getController();

        FXMLLoader productOutViewLoader = new FXMLLoader(agentFinderModule.getClass().getResource("/productOutput.fxml"), null, builderFactory, guiceControllerFactory);
        Pane productOutView = (Pane) productOutViewLoader.load();
        final ProductOutputController controller2 = productOutViewLoader.getController();
*/




//        Pair<Parent,String> stockPair = new Pair<Parent, String>( stockView , "stockView" );

//        agentFinderModule.configurePanels( loginPair , productInPair , stockPair , productOutPair );
        fxPanels = injector.getInstance(FXPanels.class);
        fxPanels.register(agentFinderModule, builderFactory, guiceControllerFactory , primaryStage );

        StackPane loginView = fxPanels.addPanel("/login.fxml", "LoginView");
        fxPanels.addPanel("/depoStokEkrani.fxml", "StockView");
        fxPanels.addPanel("/productInput.fxml", "ProductInView");
        fxPanels.addPanel("/productOutput.fxml", "ProductOutView");
        fxPanels.addPanel("/mainpanel.fxml", "MainPanel");
/*
        fxPanels.setByName("LoginView", loginView);
        fxPanels.setByName("StockView", stockView);
        fxPanels.setByName("ProductInView", productInView);
        fxPanels.setByName("ProductOutView", productOutView);
        fxPanels.setByName("MainPanel", mainView);
*/

        fxPanels.addPanel("/wait.fxml", "WAIT");

        dbManager = injector.getInstance(DBManager.class);

        fxPanels.viewPanel(PanelNames.WAIT);


        final NetworkThread networkThread = injector.getInstance(NetworkThread.class);
        networkThread.aSyncOperation( loginView , new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                dbManager.connectNTLM();
                return null;
            }
        }, new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object result) {
                fxPanels.viewPanel(PanelNames.MAIN);
            }

            @Override
            public void onFailure(Throwable t) {
                fxPanels.viewPanel(PanelNames.LOGIN);
            }
        });



//        Futures.addCallback(listenableFuture, new FutureCallback<Object>() {
//            @Override
//            public void onSuccess(Object o) {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        fxPanels.viewPanel(PanelNames.MAIN);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        fxPanels.viewPanel(PanelNames.LOGIN);
//                    }
//                });
//            }
//        });


    }
    /**
     * Sets the java library path to the specified path
     *
     * @param path the new library path
     * @throws Exception
     */
    public static void setLibraryPath(String path) throws Exception {
        System.setProperty("java.library.path", path);

        //set sys_paths to null
        final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
        sysPathsField.setAccessible(true);
        sysPathsField.set(null, null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final AgentFinderModule agentFinderModule = new AgentFinderModule();
        final Injector injector = Guice.createInjector(agentFinderModule);
        GlobalDatas.getInstance().setInjector(injector);
        GuiceControllerFactory guiceControllerFactory = new GuiceControllerFactory(injector);
        setupPanels(injector, agentFinderModule, injector.getInstance(BuilderFactory.class), guiceControllerFactory);


//        final DBManager dbManager = injector.getInstance(DBManager.class);


        final BorderPane mainBorder = injector.getInstance(Key.get(BorderPane.class, Names.named("MainBorder")));
//        final StackPane mainPanel = injector.getInstance(Key.get(StackPane.class, Names.named("MainPanel")));
        Scene scene = new Scene(mainBorder, 1215, 800);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        Image image = new Image(getClass().getResourceAsStream("/icon/window/shark1.png"));
        Image image2 = new Image(getClass().getResourceAsStream("/icon/window/shark3.png"));
        Image image3 = new Image(getClass().getResourceAsStream("/icon/window/shark2.png"));
        primaryStage.getIcons().addAll(image,image2,image3);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
//        primaryStage.setFullScreen(true);
        primaryStage.show();
        this.primaryStage = primaryStage;
//        mainBorder.setCenter(loginView);

    }
    public static void main(String[] args) throws Exception {
        LogManager.getLogManager().reset();

        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        System.out.println("JVM Bit size: " + System.getProperty("sun.arch.data.model"));
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
                @Override
                public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
                    if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_F12) {
                        try {
                            ThermalPrinter.PrintLastString();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (PrintException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

                }

                @Override
                public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

                }
            });
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
//        NativeUtilities.setLibraryPath( path.toAbsolutePath().toString() );
        launch();
    }

}
