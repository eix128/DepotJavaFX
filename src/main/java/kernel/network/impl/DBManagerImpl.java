package kernel.network.impl;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import kernel.events.ConnectionException;
import kernel.network.DBManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Properties;

/**
 * Created by kadir.basol on 16.3.2016.
 */
@Singleton
public class DBManagerImpl implements DBManager {

    private EntityManager entityManager;
    private EntityManagerFactory mainData;

    @Inject
    @Named("connectionNTLM")
    private String service;

    @Inject
    @Named("connection")
    private String connection;

    @Inject
    public DBManagerImpl() {
//        stringMap.put("javax.persistence.jdbc.user",jdbcUrl);
//        stringMap.put("javax.persistence.jdbc.password",jdbcUrl);
    }

    @Override
    public boolean connect(@NotNull String userName, @NotNull String password) throws ConnectionException {
        try {
            Properties stringMap = new Properties();
            stringMap.put("javax.persistence.jdbc.url", connection );
            stringMap.put("javax.persistence.jdbc.user", userName);
            stringMap.put("javax.persistence.jdbc.password", password);
            mainData = Persistence.createEntityManagerFactory("MainData", stringMap);
            entityManager = mainData.createEntityManager();
        } catch (Throwable e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean connectNTLM() throws ConnectionException {
//        final String value = connectionNTLM.value();
        Properties stringMap = new Properties();
        stringMap.put("javax.persistence.jdbc.url", service);
        try {
            mainData = Persistence.createEntityManagerFactory("MainData", stringMap);
            final EntityManager entityManager = mainData.createEntityManager();
            this.entityManager = entityManager;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean disconnect() {
        try {
            if (entityManager != null) {
                try {
                    entityManager.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (mainData != null) {
                mainData.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public EntityManager get() {
        return entityManager;
    }

    @Override
    public EntityManager getNew() {
        return mainData.createEntityManager();
    }
}
