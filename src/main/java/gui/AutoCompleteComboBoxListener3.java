//package gui;
//
//import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
//import gui.controller.MainPanelController;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.EventHandler;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.ListView;
//import javafx.scene.control.TextField;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//
//public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {
//
//    private ComboBox comboBox;
//    private StringBuilder sb;
//    private ObservableList<T> data;
//    private boolean moveCaretToPos = false;
//    private int caretPos;
//
//    public AutoCompleteComboBoxListener(final ComboBox comboBox) {
//        this.comboBox = comboBox;
//        sb = new StringBuilder();
//        data = comboBox.getItems();
//
//        this.comboBox.setEditable(true);
//        this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
//
//            @Override
//            public void handle(KeyEvent t) {
//                comboBox.hide();
//            }
//        });
//        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
//    }
//
//    public AutoCompleteComboBoxListener(final ComboBox comboBox , MainPanelController ma) {
//        this(comboBox);
//    }
//
//    @Override
//    public void handle(KeyEvent event) {
//
//        if(event.getCode() == KeyCode.UP) {
//            caretPos = -1;
//            moveCaret(comboBox.getEditor().getText().length());
//            return;
//        } else if(event.getCode() == KeyCode.DOWN) {
//            if(!comboBox.isShowing()) {
//                comboBox.show();
//            }
//            caretPos = -1;
//            TextField editor = comboBox.getEditor();
//            String text = editor.getText();
//            if(text != null)
//                moveCaret(text.length());
//            return;
//        } else if(event.getCode() == KeyCode.BACK_SPACE) {
//            moveCaretToPos = true;
//            caretPos = comboBox.getEditor().getCaretPosition();
//        } else if(event.getCode() == KeyCode.DELETE) {
//            moveCaretToPos = true;
//            caretPos = comboBox.getEditor().getCaretPosition();
//        }
//
//        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
//                || event.isControlDown() || event.getCode() == KeyCode.HOME
//                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
//            return;
//        }
//
//        ObservableList list = FXCollections.observableArrayList();
//        for (int i=0; i<data.size(); i++) {
//            if(data.get(i).toString().toLowerCase().startsWith(
//                    this.comboBox
//                            .getEditor().getText().toLowerCase())) {
//                list.add(data.get(i));
//            }
//        }
//        String t = comboBox.getEditor().getText();
//
//        comboBox.setItems(list);
//        comboBox.getEditor().setText(t);
//        if(!moveCaretToPos) {
//            caretPos = -1;
//        }
//        moveCaret(t.length());
//        if(!list.isEmpty()) {
//            comboBox.show();
//        }
//    }
//
//    private void moveCaret(int textLength) {
//        if(caretPos == -1) {
//            comboBox.getEditor().positionCaret(textLength);
//        } else {
//            comboBox.getEditor().positionCaret(caretPos);
//        }
//        moveCaretToPos = false;
//    }
//
//
//    public synchronized void selectClosestResultBasedOnTextFieldValue(boolean affect, boolean inFocus) {
//        ObservableList items = gui.AutoCompleteComboBoxListener.this.comboBox.getItems();
//        boolean found = false;
//        if(items == null)return;
//
//        for (int i=0; i<items.size(); i++) {
//            if (items.get(i) != null && gui.AutoCompleteComboBoxListener.this.comboBox.getEditor().getText() != null && gui.AutoCompleteComboBoxListener.this.comboBox.getEditor().getText().toLowerCase().equals(items.get(i).toString().toLowerCase())) {
//                try {
//                    ListView lv = ((ComboBoxListViewSkin) gui.AutoCompleteComboBoxListener.this.comboBox.getSkin()).getListView();
//                    lv.getSelectionModel().select(i);
//                    lv.scrollTo(lv.getSelectionModel().getSelectedIndex());
//                    System.out.println(comboBox.getSelectionModel().getSelectedItem());
//                    found = true;
//                } catch (Exception ignored) {
//                }
//            }
//        }
//
//        String s = comboBox.getEditor().getText();
//        System.out.println("Found? " + found);
//        if (!found && affect) {
//            comboBox.getSelectionModel().clearSelection();
//            comboBox.getEditor().setText(s);
//            comboBox.getEditor().end();
//        }
//
//        if (!found) {
//            comboBox.getEditor().setText(null);
//            comboBox.getSelectionModel().select(null);
//            comboBox.setValue(null);
//        }
////            KeyEvent ke = new KeyEvent(comboBox, KeyCode.ENTER.toString(), KeyCode.ENTER.getName(), KeyCode.ENTER.impl_getCode(), false, false, false, false, KeyEvent.KEY_RELEASED);
//
//        if (!inFocus && comboBox.getEditor().getText() != null && comboBox.getEditor().getText().trim().length() > 0) {
//            // press enter key programmatically to have this entry added
//            KeyEvent ke = new KeyEvent(KeyEvent.KEY_RELEASED, KeyCode.ENTER.toString(), KeyCode.ENTER.toString(), KeyCode.ENTER, false, false, false, false);
//            comboBox.fireEvent(ke);
//        }
//    }
//}