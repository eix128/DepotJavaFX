package jpa.listeners;

import com.google.common.base.Supplier;
import com.google.inject.Inject;
import com.google.inject.Injector;
import globals.GlobalDatas;
import globals.interfaces.PostInit;
import jpa.DepotsEntity;
import kernel.network.DBManager;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Kadir on 3/26/2016.
 */
public class IDepotsEntityListener  {
//    implements DepotsEntityListener
//    @Inject
//    DBManager dbManager;

    @PostInit
    public static void init() {
        Injector injector = GlobalDatas.getInstance().getInjector();
        DBManager injectorInstance = injector.getInstance(DBManager.class);
        EntityManagerFactory emf = injectorInstance.getEMF();
        CriteriaBuilder criteriaBuilder = emf.getCriteriaBuilder();
        CriteriaQuery<DepotsEntity> query = criteriaBuilder.createQuery(DepotsEntity.class);
        CriteriaQuery<DepotsEntity> query1 = criteriaBuilder.createQuery(DepotsEntity.class);
        Root<DepotsEntity> from = query.from(DepotsEntity.class);
        CriteriaQuery<DepotsEntity> select = query1.select(from);
        EntityManager entityManager = injectorInstance.get();
        TypedQuery<DepotsEntity> query2 = entityManager.createQuery(select);

        Supplier<List<DepotsEntity>> depotsEntitySupplier = new Supplier<List<DepotsEntity>>() {
            @Override
            public List<DepotsEntity> get() {
                return query2.getResultList();
            }
        };
//        getAll = depotsEntitySupplier;
    }
}
