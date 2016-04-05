package main.gui.utils;

import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * Created by kadir.basol on 28.3.2016.
 */
public class AutoNext {

    public static interface Selector {
        public boolean getSelected();
    }

    public static final void setAutoNext(Pane borderPane,Selector selector) {
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

                if (selector.getSelected()) {
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
    }
}
