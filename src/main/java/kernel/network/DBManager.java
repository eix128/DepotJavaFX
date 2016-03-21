package kernel.network;

import kernel.events.ConnectionException;

import javax.persistence.EntityManager;

/**
 * Created by kadir.basol on 16.3.2016.
 */
public interface DBManager {
    public boolean connect(String userName,String password) throws ConnectionException;
    public boolean connectNTLM(  ) throws ConnectionException;
    public boolean disconnect();
    public EntityManager get();
    public EntityManager getNew();
}
