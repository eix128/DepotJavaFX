package jpa.utils;

import com.google.inject.Injector;
import globals.GlobalDatas;
import jpa.DepotsEntity;
import kernel.network.DBManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Kadir on 3/26/2016.
 */
public class JPAUtils {
     public static <T> TypedQuery<T>  getAllData(Class<T> clazz) {
        Injector injector = GlobalDatas.getInstance().getInjector();
        DBManager injectorInstance = injector.getInstance(DBManager.class);
         CriteriaBuilder criteriaBuilder = injectorInstance.getCriteriaBuilder();
         CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
        Root<T> from = query.from(clazz);
        CriteriaQuery<T> select = query.select(from);
        EntityManager entityManager = injectorInstance.get();
        TypedQuery<T> query2 = entityManager.createQuery(select);
         return query2;
    }


    public static <T> TypedQuery<T>  getAllDataLimit(Class<T> clazz,int begin,int maximum) {
        Injector injector = GlobalDatas.getInstance().getInjector();
        DBManager injectorInstance = injector.getInstance(DBManager.class);
        CriteriaBuilder criteriaBuilder = injectorInstance.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
        Root<T> from = query.from(clazz);
        CriteriaQuery<T> select = query.select(from);
        EntityManager entityManager = injectorInstance.get();
        TypedQuery<T> query2 = entityManager.createQuery(select);
        query2.setFirstResult(begin);
        query2.setMaxResults(maximum);
        return query2;
    }



    public static <T> TypedQuery<T>  getAllDataWhere(Class<T> clazz, int begin, int maximum , Predicate... predicates) {
        Injector injector = GlobalDatas.getInstance().getInjector();
        DBManager injectorInstance = injector.getInstance(DBManager.class);
        CriteriaBuilder criteriaBuilder = injectorInstance.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
        Root<T> from = query.from(clazz);
        CriteriaQuery<T> select = query.select(from);
        CriteriaQuery<T> where = select.where(predicates);
        EntityManager entityManager = injectorInstance.get();
        TypedQuery<T> query2 = entityManager.createQuery(where);
        query2.setFirstResult(begin);
        query2.setMaxResults(maximum);
        return query2;
    }
}
