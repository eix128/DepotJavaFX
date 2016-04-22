package gui.utils;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import jpa.SettableString;

import java.util.concurrent.Callable;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

/**
 * Created by kadir.basol on 4/15/2016.
 */
public class ComboBoxUtils {
    public static final Object getValue(ComboBox<? extends SettableString> comboBox) {
        final Object value = comboBox.getValue();
        if(value instanceof String) {
            final ObservableList items = comboBox.getItems( );
            IntStream.range( 0 , items.size() ).sequential().mapToObj(new IntFunction<Object>() {
                @Override
                public Object apply(int value2) {
                    final Object o = items.get(value2);
                    if(o != null) {
                        if (o.equals(value)) {
                            return (SettableString)o;
                        }
                    }
                    return null;
                }
            });
        } else {
            return value;
        }
        return value;
    }
}
