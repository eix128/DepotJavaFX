package gui.controller;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import globals.interfaces.ControllerNetworkInit;
import gui.FXPanels;
import gui.PanelNames;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import kernel.events.ConnectionException;
import kernel.network.DBManager;
import kernel.threads.ThreadManager;
import kernel.threads.network.NetworkThread;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

public class LoginController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField textUserName;

    @FXML
    private PasswordField textPassword;

    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonNTLM;


    private boolean buttonLoginSelected;


    @Inject
    DBManager dbManager;

    @Inject
    NetworkThread networkThread;

    @Inject
    ThreadManager manager;

    @Inject
    private FXPanels fxPanels;

    public LoginController() {

    }


    @ControllerNetworkInit
    public final void postLoad() {
        System.out.println("Login Controller Cagrildi");
    }

    @FXML
    public void onLogin(ActionEvent event) {
//        System.out.println(event);
        String text = textPassword.getText();
        String text1 = textUserName.getText();
        networkThread.aSyncOperation(null, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                boolean connect = dbManager.connect(text1, text);
                return new Boolean(connect);
            }
        }, new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object result) {
                Boolean value = (Boolean) result;
                if (value)
                    fxPanels.viewPanel(PanelNames.MAIN);
            }

            @Override
            public void onFailure(Throwable t) {
                fxPanels.viewPanel(PanelNames.LOGIN);
            }
        });
    }

    @FXML
    public void onNTLMPressed(ActionEvent event) {
//        System.out.println("Deneme 1 2 3");
        networkThread.aSyncOperation(null, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                boolean b = false;
                b = dbManager.connectNTLM();
                return new Boolean(b);
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
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        buttonLoginSelected = false;
        borderPane.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) ->
        {
            if (event.getCode() == KeyCode.ENTER) {
                KeyEvent newEvent
                        = new KeyEvent(
                        null,
                        null,
                        KeyEvent.KEY_PRESSED,
                        "",
                        "\t",
                        KeyCode.TAB,
                        event.isShiftDown(),
                        event.isControlDown(),
                        event.isAltDown(),
                        event.isMetaDown()
                );

                if (!buttonLoginSelected) {
                    Event.fireEvent(event.getTarget(), newEvent);
                    event.consume();
                } else {
                    newEvent = new KeyEvent(
                            null,
                            null,
                            KeyEvent.KEY_PRESSED,
                            "",
                            "",
                            KeyCode.SPACE,
                            event.isShiftDown(),
                            event.isControlDown(),
                            event.isAltDown(),
                            event.isMetaDown()
                    );
                    Event.fireEvent(event.getTarget(), newEvent);
                    event.consume();
                }
            }
        });

        buttonLogin.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue.booleanValue()) {
                    focusGained(borderPane);
                } else {
                    focusLost(borderPane);
                }
            }
        });


        Platform.runLater(() -> {
                    textUserName.requestFocus();
                }
        );
    }

    private void focusGained(BorderPane stackPane) {
        buttonLoginSelected = true;
    }

    private void focusLost(BorderPane stackPane) {
        buttonLoginSelected = false;
    }
}
