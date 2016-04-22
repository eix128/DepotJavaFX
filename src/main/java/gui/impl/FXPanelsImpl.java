package gui.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import globals.interfaces.ControllerNetworkInit;
import gui.FXPanels;
import gui.PanelNames;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.BuilderFactory;
import javafx.util.Callback;
import main.AgentFinderModule;
import org.javatuples.Pair;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by kadir.basol on 25.3.2016.
 */
@Singleton
public class FXPanelsImpl implements FXPanels {

    private HashMap<String,Pair<Pane,Object>>  hashMap;

    @Inject
    @Named(value = "MainBorder")
    private BorderPane borderPane;


    private Pane activePanel;
    private AgentFinderModule agentFinderModule;
    private BuilderFactory builderFactory;
    private Callback<Class<?>, Object> guiceControllerFactory;
    private Stage primaryStage;

    public FXPanelsImpl() {
        activePanel = null;
        hashMap = new HashMap<>( );
    }

    @Override
    public Pair< Pane, Object > getByName(String name) {
        return hashMap.get(name);
    }

    @Override
    public void setByName(String name, Pane pane , Object controller ) {
        hashMap.put(name,new Pair<>(pane,controller));
    }


    public final void viewPanel(PanelNames panelNames) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                switch (panelNames) {
                    case LOGIN:
                        final Pair<Pane,Object> loginView = getByName("LoginView");
                        borderPane.setCenter(loginView.getValue0());
                        activePanel = loginView.getValue0();
                        break;
                    case VIEW:
                        final Pair<Pane,Object> stockView = getByName("StockView");
                        borderPane.setCenter(stockView.getValue0());
                        activePanel = stockView.getValue0();
                        break;
                    case PRODUCT_IN:
                        break;
                    case PRODUCT_OUT:
                        break;
                    case MAIN:
                        final Pair<Pane,Object> mainPanel = getByName("MainPanel");
                        borderPane.setCenter(mainPanel.getValue0());
                        activePanel = mainPanel.getValue0();
                        break;
                    case WAIT:
                        final Pair<Pane,Object> waitPanel = getByName("WAIT");
                        borderPane.setCenter(waitPanel.getValue0());
                        activePanel = waitPanel.getValue0();
                        break;
                }
            }
//                Pane wait = getByName("WAIT");
//                if(activePanel ==  wait && panelNames != PanelNames.WAIT) {
//                    FadeTransition ft = new FadeTransition(Duration.millis(2000), wait );
//                    ft.setFromValue(1.0);
//                    ft.setToValue(0.0);
//                    System.out.println("Done");
////                    ft.setCycleCount(Timeline.INDEFINITE);
//                    ft.onFinishedProperty().set(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent actionEvent) {
//                        }
//                    });
//                    ft.play();
//                } else {
//                    final Pane waitPanel = getByName("WAIT");
//                    borderPane.setCenter(waitPanel);
//                }
//
//            }
        });

    }


    public final StackPane addPanel(String resource, String name) throws IOException {
        FXMLLoader productOutViewLoader= new FXMLLoader(getClass().getResource(resource), null, builderFactory, guiceControllerFactory);
        final StackPane load = (StackPane) productOutViewLoader.load();
        hashMap.put(name,new Pair<>(load,productOutViewLoader.getController()));
        return load;
    }


    public final void onNetworkAccess() {
        for (Pair<Pane,Object> value : hashMap.values()) {
//            value.getValue0()
            final Object controller = value.getValue1();
            if(controller != null) {
//                final Annotation[] annotations = controller.getClass().getAnnotations();
                final Method[] methods = controller.getClass().getMethods();
                for (Method method : methods) {
                    final Annotation[] annotations1 = method.getAnnotations();
                    for (Annotation annotation : annotations1) {
                        if (annotation.annotationType() == ControllerNetworkInit.class) {
                            try {
                                method.invoke(controller);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public Pane getActivePanel() {
        return activePanel;
    }


    @Override
    public void register(AgentFinderModule agentFinderModule, BuilderFactory builderFactory, Callback<Class<?>, Object> guiceControllerFactory, Stage primaryStage) {
        this.agentFinderModule = agentFinderModule;
        this.builderFactory = builderFactory;
        this.guiceControllerFactory = guiceControllerFactory;
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
