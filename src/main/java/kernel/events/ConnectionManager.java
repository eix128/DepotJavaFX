package kernel.events;

/**
 * Created by kadir.basol on 17.3.2016.
 */
public interface ConnectionManager {
    public void addListener( ConnectionListener connectionListener );
    public void removeListener( ConnectionListener connectionListener );
}
