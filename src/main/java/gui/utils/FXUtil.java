package gui.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FxUtil {

    public enum AutoCompleteMode {
        STARTS_WITH,CONTAINING,;
    }


    public static<T> void autoCompleteComboBox(ComboBox<T> comboBox, AutoCompleteMode mode) {
        final String[] some = new String[1];
        final String[] typedText = new String[1];
        StringBuilder sb = new StringBuilder();

        ObservableList<T> data = comboBox.getItems();

        comboBox.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event){

                comboBox.hide();
            }
        });
        comboBox.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            private boolean moveCaretToPos = false;
            private int caretPos;
            public void handle(KeyEvent event) {

                String keyPressed = event.getCode().toString().toLowerCase();



                if ("space".equals(keyPressed) ){
                    typedText[0] = " ";
                } else if ("shift".equals(keyPressed ) || "command".equals(keyPressed)
                        || "alt".equals(keyPressed) ) {

                    return;
                } else  {
                    typedText[0] = event.getCode().toString().toLowerCase();
                }


                if (event.getCode() == KeyCode.UP) {
                    caretPos = -1;
                    moveCaret(comboBox.getEditor().getText().length());
                    return;
                } else if (event.getCode() == KeyCode.DOWN) {
                    if (!comboBox.isShowing()) {
                        comboBox.show();
                    }
                    caretPos = -1;
                    moveCaret(comboBox.getEditor().getText().length());
                    return;
                } else if (event.getCode() == KeyCode.BACK_SPACE) {
                    moveCaretToPos = true;

                    caretPos = comboBox.getEditor().getCaretPosition();
                    typedText[0] =null;
                    sb.delete(0, sb.length());
//                    comboBox.getEditor().setText(null);
                    return;

                } else if (event.getCode() == KeyCode.DELETE) {
                    moveCaretToPos = true;
                    caretPos = comboBox.getEditor().getCaretPosition();
                    return;
                } else if (event.getCode().equals(KeyCode.TAB)) {

                    some[0] = null;
                    typedText[0] = null;
                    sb.delete(0, sb.length());
                    return;
                } else if (event.getCode() == KeyCode.LEFT
                        || event.isControlDown() || event.getCode() == KeyCode.HOME
                        || event.getCode() == KeyCode.END || event.getCode() == KeyCode.RIGHT) {

                    return;
                }


                if (typedText[0] ==null){
                    typedText[0] = comboBox.getEditor().getText().toLowerCase();
                    sb.append(typedText[0]);

                }else{
                    System.out.println("sb:"+sb);
                    System.out.println("tt:"+ typedText[0]);

                    sb.append(typedText[0]);


                }

                ObservableList<T> list = FXCollections.observableArrayList();
                for (T aData : data) {
                    String adata = aData.toString();
                    String text = comboBox.getEditor().getText();
                    if(text == null)
                        continue;
                    if(adata != null) {
                        if (mode.equals(AutoCompleteMode.STARTS_WITH) && adata.toLowerCase().startsWith(sb.toString())) {
                            list.add(aData);
                            some[0] = aData.toString();
                        } else if (mode.equals(AutoCompleteMode.CONTAINING) && adata.toLowerCase().contains(text.toLowerCase())) {
                            list.add(aData);
                        }
                    }
                }


                comboBox.setItems(list);

//                comboBox.getEditor().setText(some[0]);



                comboBox.getEditor().positionCaret(sb.length());
                comboBox.getEditor().selectEnd();
                if (!moveCaretToPos) {
                    caretPos = -1;
                }

                if (!list.isEmpty()) {
                    comboBox.show();
                }
            }

            private void moveCaret(int textLength) {
                if (caretPos == -1) {
                    comboBox.getEditor().positionCaret(textLength);
                } else {
                    comboBox.getEditor().positionCaret(caretPos);
                }
                moveCaretToPos = false;
            }
        });
    }


    public static<T> T getComboBoxValue(ComboBox<T> comboBox){
        if (comboBox.getSelectionModel().getSelectedIndex() < 0) {
            return null;
        } else {
            return comboBox.getItems().get(comboBox.getSelectionModel().getSelectedIndex());
        }
    }
}