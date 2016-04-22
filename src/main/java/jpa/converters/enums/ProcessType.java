package jpa.converters.enums;

/**
 * Created by kadir.basol on 21.3.2016.
 */

import com.google.inject.Inject;
import com.google.inject.Injector;
import globals.GlobalDatas;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import jpa.SettableString;
import main.gui.ComboList;

import java.util.ResourceBundle;

/**
 * Giriş çıkış türü
 */
public enum ProcessType implements SettableString {
    INPUT(0) {
        @Override
        public void setString(String data) {
            ComboList instance1 = GlobalDatas.getInstance().getInjector().getInstance(ComboList.class);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println("aaa "+ProcessType.INPUT);
                    instance1.getByName("Main.transactionType").setValue(ProcessType.INPUT);
                }
            });
        }


    },OUTPUT(1) {

        @Override
        public void setString(String data) {
            ComboList instance1 = GlobalDatas.getInstance().getInjector().getInstance(ComboList.class);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    instance1.getByName("Main.transactionType").setValue(ProcessType.OUTPUT);
                }
            });
        }

    },UNKNOWN(-2) {
        @Override
        public void setString(String data) {
            ComboList instance1 = GlobalDatas.getInstance().getInjector().getInstance(ComboList.class);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    instance1.getByName("Main.transactionType").setValue(ProcessType.UNKNOWN);
                }
            });
        }
    },ALL(-1) {
        @Override
        public void setString(String data) {
            ComboList instance1 = GlobalDatas.getInstance().getInjector().getInstance(ComboList.class);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    instance1.getByName("mainPanel.all").setValue(ProcessType.ALL);
                }
            });
        }
    };


    private int i;


    private ResourceBundle resourceBundle;



    ProcessType(int i) {
        resourceBundle = GlobalDatas.getInstance().getInjector().getInstance(ResourceBundle.class);
        this.i = i;
    }


    public ProcessType getValue() {
        return this;
    }

    public static ProcessType fromInteger(int i) {
        switch (i) {
            case 0:
                return ProcessType.INPUT;
            case 1:
                return ProcessType.OUTPUT;
            case -2:
                return ProcessType.ALL;
            default:
                return ProcessType.UNKNOWN;
        }
    }

    @Override
    public String getString() {
        switch (this) {
            case INPUT:
                return resourceBundle.getString("mainPanel.inputType");
            case OUTPUT:
                return  resourceBundle.getString("mainPanel.outputType");
            case UNKNOWN:
                return resourceBundle.getString("mainPanel.unknown");
            case ALL:
                return resourceBundle.getString("mainPanel.all");
        }
        return resourceBundle.getString("mainPanel.unknown");
    }

    @Override
    public String toString() {
        switch (this) {
            case INPUT:
                return resourceBundle.getString("mainPanel.inputType");
            case OUTPUT:
                return  resourceBundle.getString("mainPanel.outputType");
            case UNKNOWN:
                return resourceBundle.getString("mainPanel.unknown");
            case ALL:
                return resourceBundle.getString("mainPanel.all");
        }
        return resourceBundle.getString("mainPanel.unknown");
    }

    public static Integer toInteger(ProcessType processType) {
        return processType.getIntValue();
    }


    public final int getIntValue() {
        return i;
    }
}
