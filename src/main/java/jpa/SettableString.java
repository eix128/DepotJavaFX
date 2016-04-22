package jpa;

import javafx.scene.control.ComboBox;

/**
 * Created by kadir.basol on 4/12/2016.
 */
public interface SettableString {
    public void setString(String data);
    public String getString();
    public SettableString getValue();
    public String toString();
}
