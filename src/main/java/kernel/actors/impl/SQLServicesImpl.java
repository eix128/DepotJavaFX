package kernel.actors.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import kernel.actors.SQLServices;
import kernel.network.DBManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by kadir.basol on 22.3.2016.
 */
@Singleton
public class SQLServicesImpl implements SQLServices {

    @Inject
    DBManager dbManager;

    @Override
    public String getUserName() {
        final String s;
        final EntityManager entityManager = dbManager.get();
        final Query nativeQuery = entityManager.createNativeQuery("SELECT SYSTEM_USER");
        s = nativeQuery.getSingleResult().toString();
        return s;
    }

    @Override
    public int generatePartNo() {
        final EntityManager entityManager = dbManager.get();
        final Query nativeQuery = entityManager.createNativeQuery("SELECT CONVERT(VARCHAR(10),GETDATE(),111)");
        String s = nativeQuery.getSingleResult().toString();
        final String[] split = s.split("/");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = split.length - 1 ; i >= 0 ; i--) {
            final String s1 = split[i];
            stringBuilder.append(s1.length() == 1 ? '0' + s1 : s1);
        }
        return Integer.valueOf(stringBuilder.toString());
    }
}
