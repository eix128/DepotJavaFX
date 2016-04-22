package gui.events;

import jpa.ProcessEntity;

/**
 * Created by kadir.basol on 5.4.2016.
 */
public interface DepotManagerEventListener {
    public void onNewProcessAdded( ProcessEntity processEntity ) ;
    public void onNewProcessRemoved( ProcessEntity processEntity );
    public void onRefreshClicked();
    public void onClearAllClicked();
}
