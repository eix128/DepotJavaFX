package kernel.actors.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import globals.interfaces.ControllerNetworkInit;
import jpa.PartNoIndexEntity;
import jpa.TotalProductsEntity;
import jpa.converters.enums.ProcessType;
import jpa.converters.enums.UnitType;
import jpa.utils.JPAUtils;
import kernel.actors.SQLServices;
import kernel.network.DBManager;
import language.LangUtils;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by kadir.basol on 22.3.2016.
 */
@Singleton
public class SQLServicesImpl implements SQLServices {

    @Inject
    private DBManager dbManager;


    @Inject
    private LangUtils   langUtils;


    @ControllerNetworkInit
    public void init() {

    }


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

    @Override
    public long getTotalUnits( ) {
        EntityManager entityManager = dbManager.get();
        Query nativeQuery = entityManager.createNativeQuery("SELECT SUM( totalProducts.units ) FROM dbo.totalProducts");
        return (long) nativeQuery.getSingleResult();
    }

    @Override
    public long getTotalUnitsBy(UnitType unitType) {
        EntityManager entityManager = dbManager.get();
        Query nativeQuery = entityManager.createNativeQuery("SELECT SUM( totalProducts.units ) FROM dbo.totalProducts WHERE totalProducts.unitType = " + unitType.getValue());
        Object singleResult = nativeQuery.getSingleResult();
        if(singleResult == null)
            return 0;
        return (long) singleResult;

    }

    @Override
    public long getTotalUnitsBy(UnitType unitType, ProcessType processType, int depotId , int companyId, int productId) {
        CriteriaBuilder criteriaBuilder = dbManager.getCriteriaBuilder();
        EntityManager entityManager = dbManager.get();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();

        Root<TotalProductsEntity> entityRoot = query.from(TotalProductsEntity.class);


        Path<Integer> companyid = entityRoot.get("companyId");
        Path<Integer> depotid = entityRoot.get("depotId");
        Path<Integer> productid = entityRoot.get("productId");
        Path<Long> units = entityRoot.get("units");
        Predicate lastPred = null;


        if(companyId > 0) {
            if (lastPred != null) {
                lastPred = criteriaBuilder.and(criteriaBuilder.equal(companyid, companyId), lastPred);
            } else {
                lastPred = (criteriaBuilder.equal(companyid, companyId));
            }
        }

        if(productId > 0) {
            if (lastPred != null) {
                lastPred = criteriaBuilder.and(criteriaBuilder.equal(productid, productId), lastPred);
            } else {
                lastPred = (criteriaBuilder.equal(productid, productId));
            }
        }
        if (depotId > 0) {
            if(lastPred != null) {
                lastPred = criteriaBuilder.and(criteriaBuilder.equal(depotid, depotId ), lastPred);
            }
            else {
                lastPred = (criteriaBuilder.equal(depotid, depotId));
            }
        }

        Expression<Long> sum = criteriaBuilder.sum(units);
        CriteriaQuery<Tuple> multiselect = query.multiselect(sum);
        CriteriaQuery<Tuple> where = null;
        if(lastPred != null)
            where = multiselect.where(lastPred);
        else
            where = multiselect;

        TypedQuery<Tuple> query1 = entityManager.createQuery(where);
        Tuple singleResult = query1.getSingleResult();
        List<TupleElement<?>> elements = singleResult.getElements();

        if(elements == null)
            return 0;

        Long sum1 = singleResult.get( 0 , Long.class);
        if(sum1 != null)
            return sum1;
        else
            return 0;
    }


    @Override
    public String getTime() {
        final EntityManager entityManager = dbManager.get();

//        final Query nativeQuery = entityManager.createNativeQuery("SET LANGUAGE Turkish;)");
        final Query nativeQuery2 = entityManager.createNativeQuery("SELECT CONVERT(VARCHAR(19),GETDATE(),120)");
        try {
            final String result = String.valueOf(nativeQuery2.getSingleResult());
            final String[] split = result.split(" ");

            final String[] split1 = split[0].split("-");
            final String year = split1[0];
            final String month = split1[1];
            final String day = split1[2];

            final Integer yearInt = Integer.valueOf(year);
            final Integer monthInt = Integer.valueOf(month);
            final Integer dayInt = Integer.valueOf(day);


            final String[] split2 = split[1].split(":");
            final String hour = split2[0];
            final String mins = split2[1];

            final Integer hourInt = Integer.valueOf(hour);
            final Integer MinInt = Integer.valueOf(mins);

            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append(day).append("/").append(months.get(monthInt - 1)).append("/").append(year);
            stringBuilder.append(day).append("/").append(langUtils.getMonthByIndex( monthInt - 1 )).append("/").append(year).append(" ").append(hourInt).append(":").append(MinInt);
            return stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
