package main.gui.impl;

import com.google.inject.Singleton;
import javafx.scene.control.ComboBox;
import main.gui.ComboList;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kadir.basol on 4/14/2016.
 */
@Singleton
public class ComboListImpl implements ComboList {

    private final ConcurrentHashMap<String,ComboBox> boxConcurrentHashMap;

    public ComboListImpl() {
        boxConcurrentHashMap = new ConcurrentHashMap<>( );
    }

    @Override
    public ComboBox getByName(String name) {
        return boxConcurrentHashMap.get(name);
    }

    @Override
    public void setByName(String name,ComboBox comboBox) {
        boxConcurrentHashMap.put(name,comboBox);
    }
}
