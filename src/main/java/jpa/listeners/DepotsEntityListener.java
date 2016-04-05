package jpa.listeners;

import com.google.common.base.Supplier;
import jpa.DepotsEntity;

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
public interface DepotsEntityListener {

    public void init(DepotsEntity depotsEntity);
}
