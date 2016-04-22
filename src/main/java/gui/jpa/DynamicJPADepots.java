package gui.jpa;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Injector;
import globals.GlobalDatas;
import gui.items.TotalProductsItem;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import jpa.*;
import jpa.converters.enums.ProcessType;
import kernel.network.DBManager;
import language.LangUtils;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import utils.guava.LazyCache;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by kadir.basol on 5.4.2016.
 */
public class DynamicJPADepots implements ObservableList {

    private final LoadingCache<Integer, TotalProductsItem> cache;

    private LangUtils langUtils;
    private EntityManager entityManager;

    private TotalProductsItem dummyProducts = new TotalProductsItem();

    private LazyCache<Long> cacheSize;

    public DynamicJPADepots(LangUtils langUtils, EntityManager entityManager) {
        this.langUtils = langUtils;
        this.entityManager = entityManager;

        javax.persistence.criteria.CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<TotalProductsEntity> cq = cb.createQuery(TotalProductsEntity.class);



        final Root<TotalProductsEntity> rootEntry = cq.from(TotalProductsEntity.class);

//        Expression<String> postgresqlCastFunction = cb.function("CAST", String.class, from.<String>get("ID").as(String.class));
//        Predicate<String> p = cb.like(postgresqlCastFunction, "10%");

        CriteriaQuery<TotalProductsEntity> all = cq.select(rootEntry);
        TypedQuery<TotalProductsEntity> allQuery = entityManager.createQuery(all);


        cache = CacheBuilder.newBuilder()
                .maximumSize(128 * 128 * 32) // maximum 100 records can be cached
//                .maximumSize(256) // maximum 100 records can be cached
                .concurrencyLevel(4)
//                .softValues()
                .expireAfterAccess(10, TimeUnit.SECONDS) // cache will expire after 30 minutes of access
                .build(new CacheLoader<Integer, TotalProductsItem>() {
                    @Override
                    public TotalProductsItem load(Integer tableIndex) throws Exception {
                        int max = Math.max(0, tableIndex - 256);
                        allQuery.setHint(QueryHints.REFRESH, HintValues.TRUE);
                        final TypedQuery<TotalProductsEntity> cityEntityTypedQuery = allQuery.setFirstResult(max).setMaxResults(256);
                        System.out.println("Cache loaded");

                        final List<TotalProductsEntity> resultList = cityEntityTypedQuery.getResultList();
                        int counter = 0;
                        for (int i = max; i < max + resultList.size(); i++) {
                            TotalProductsEntity totalProductsEntity = resultList.get(counter);
                            totalProductsEntity.setIndex( max + i );
                            System.out.println("New Units: "+totalProductsEntity.getUnits());
                            counter++;
                        }
//                        IntStream.range(max , max + resultList.size() ).forEach( index -> {
//                            resultList.get(index).setIndex(tableIndex);
//                        });
                        final ConcurrentMap<Integer, TotalProductsItem > integerCityEntityConcurrentMap = cache.asMap();
                        TotalProductsItem processItem = integerCityEntityConcurrentMap.get(tableIndex);
                        if (processItem != null) {
                            return processItem;
                        }

                        List<TotalProductsItem> collect = resultList.parallelStream().map(new Function<TotalProductsEntity, TotalProductsItem>() {
                            @Override
                            public TotalProductsItem apply(TotalProductsEntity processEntity) {
                                TotalProductsItem from = TotalProductsItem.from(langUtils,processEntity);
                                return from;
                            }
                        }).collect(Collectors.toList());


                        collect.parallelStream().forEach(processItem22 -> {
                            cache.asMap().put(processItem22.getIndex(),processItem22);
                        });
                        final TotalProductsItem processItem3 = cache.asMap().get(tableIndex);
                        if(processItem3 != null)
                            return processItem3;
                        else
                            return null;
                        //System.out.println(tableIndex);
                    } // build the cacheloader
                });

        CriteriaQuery<Long> countCriteria = cb.createQuery(Long.class);
        Root<?> entityRoot = rootEntry;
        countCriteria.select(cb.count(countCriteria.from(TotalProductsEntity.class)));
//        countCriteria.where(countCriteria.getRestriction());
        final TypedQuery<Long> query = entityManager.createQuery(countCriteria);
        cacheSize = new LazyCache<Long>(new Supplier<Long>() {
            @Override
            public Long get() {
                return  query.getSingleResult();
            }
        });
        cacheSize.get();
        Injector injector = GlobalDatas.getInstance().getInjector();
        DBManager instance = injector.getInstance(DBManager.class);
        instance.addRefresh(cacheSize);
        Suppliers.memoizeWithExpiration(cacheSize, 5, TimeUnit.SECONDS);

    }

    @Override
    public void addListener(ListChangeListener listener) {

    }

    @Override
    public void removeListener(ListChangeListener listener) {

    }

    @Override
    public boolean addAll(Object[] elements) {
        return false;
    }

    @Override
    public boolean setAll(Object[] elements) {
        return false;
    }

    @Override
    public boolean setAll(Collection col) {
        return false;
    }

    @Override
    public boolean removeAll(Object[] elements) {
        return false;
    }

    @Override
    public boolean retainAll(Object[] elements) {
        return false;
    }

    @Override
    public void remove(int from, int to) {

    }

    @Override
    public int size() {
        return cacheSize.get().intValue();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return false;
    }

    @Override
    public void clear() {
        cache.invalidateAll();
        cacheSize.invalidate();
        cacheSize.get();
    }

    @Override
    public Object get(int index) {
        try {
            TotalProductsItem totalProductsItem = cache.get(index);
            if (totalProductsItem == null) {
                return dummyProducts;
            }
            return totalProductsItem;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return dummyProducts;
    }

    @Override
    public Object set(int index, Object element) {
        return null;
    }

    @Override
    public void add(int index, Object element) {

    }

    @Override
    public Object remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }
}
