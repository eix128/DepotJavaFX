package kernel.events.impl;

import kernel.events.ConnectionListener;
import kernel.events.enums.ConnectionErrorCode;

import javax.persistence.EntityManager;

/**
 * Created by kadir.basol on 16.3.2016.
 */
public class ConnectionListenerImpl implements ConnectionListener {

    @Override
    public void onConnectionEstablished(EntityManager entityManager) {
        
    }

    @Override
    public void onConnectionFailed(ConnectionErrorCode errorCode) {

    }

    @Override
    public void onDisconnect() {

    }
}
