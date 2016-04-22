package gui;

import javafx.scene.control.TableCell;
import jpa.ProcessEntity;

import java.time.LocalTime;

/**
 * Created by Kadir on 4/11/2016.
 */
public class GUITimeFormatter extends TableCell<ProcessEntity, LocalTime> {
    @Override
    protected void updateItem(LocalTime item, boolean empty) {
        super.updateItem(item, empty);
        if(item != null)
            setText(item.getHour()+ ":" + item.getMinute() );
    }
}
