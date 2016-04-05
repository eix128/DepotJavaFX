package main;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import javafx.scene.layout.BorderPane;

/**
 * Created by kadir.basol on 25.3.2016.
 */
public class FXBinderModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(BorderPane.class).annotatedWith(Names.named("MainBorder")).toInstance(new BorderPane());
    }
}
