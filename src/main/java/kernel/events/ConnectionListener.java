package kernel.events;

import kernel.events.enums.ConnectionErrorCode;

import javax.persistence.EntityManager;

/**
 * Created by kadir.basol on 16.3.2016.
 */
public interface ConnectionListener {
    public void onConnectionEstablished( EntityManager entityManager );
    public void onConnectionFailed( ConnectionErrorCode errorCode );
    public void onDisconnect();
}