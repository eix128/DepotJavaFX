package gui;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.BuilderFactory;
import javafx.util.Callback;
import main.AgentFinderModule;
import org.javatuples.Pair;

import java.io.IOException;

/**
 * Created by kadir.basol on 25.3.2016.
 */
public interface FXPanels {
    public Pair< Pane, Object > getByName(String name);
    public void setByName(String name, Pane pane ,Object controller);
    public void viewPanel(PanelNames panelNames);
    public Pane getActivePanel();

    public void register(AgentFinderModule agentFinderModule, BuilderFactory builderFactory, Callback<Class<?>, Object> guiceControllerFactory);

    public StackPane addPanel(String resource, String name) throws IOException;
    public void onNetworkAccess();
}
