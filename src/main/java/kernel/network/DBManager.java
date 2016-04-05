package kernel.network;

import kernel.events.ConnectionException;
import utils.guava.LazyCache;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by kadir.basol on 16.3.2016.
 */
public interface DBManager {
    public boolean connect(String userName,String password) throws ConnectionException;
    public boolean updateOrCreateObject(Object object,String namedQuery);
    public boolean connectNTLM(  ) throws ConnectionException;
    public boolean disconnect();
    public CriteriaBuilder getCriteriaBuilder();
    public EntityManager get();
    public EntityManagerFactory getEMF();
    public EntityManager getNew();
    public void refreshDB();
    public void addRefresh(LazyCache<?> lazyCache);
}
