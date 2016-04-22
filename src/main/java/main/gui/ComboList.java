package main.gui;

import javafx.scene.control.ComboBox;

/**
 * Created by kadir.basol on 4/14/2016.
 */
public interface ComboList {
    public ComboBox getByName(String name);
    public void setByName(String name,ComboBox value);
}
