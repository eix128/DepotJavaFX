package gui.jpa;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import jpa.*;
import language.LangUtils;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Kadir on 2/23/2016.
 */
public class DynamicJPA implements ObservableList {

    private ArrayList<ListChangeListener> changeListener;

    private EntityManager entityManager;
    private LoadingCache<Integer, ProcessItem> cache;
    private Supplier<Long> cacheSize;
    private LangUtils langUtils;


    public DynamicJPA(LangUtils langUtils, EntityManager entityManager) {
        this.langUtils = langUtils;
        changeListener = new ArrayList<ListChangeListener>();
        this.entityManager = entityManager;
//        entityManager.createNamedQuery("select.between", CityEntity.class);
        javax.persistence.criteria.CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<ProcessEntity> cq = cb.createQuery(ProcessEntity.class);


        final Root<ProcessEntity> rootEntry = cq.from(ProcessEntity.class);

//        Expression<String> postgresqlCastFunction = cb.function("CAST", String.class, from.<String>get("ID").as(String.class));
//        Predicate<String> p = cb.like(postgresqlCastFunction, "10%");

        CriteriaQuery<ProcessEntity> all = cq.select(rootEntry);
        TypedQuery<ProcessEntity> allQuery = entityManager.createQuery(all);


//        BigInteger bigInteger = new BigInteger("2323423423423423424234234422343323443342");
//Creating the BloomFilter
//        BloomFilter bloomFilter = BloomFilter.create(Funnels.byteArrayFunnel(), 1000);

//Putting elements into the filter
//A BigInteger representing a key of some sort
//        bloomFilter.put(bigInteger.toByteArray());

//Testing for element in set
//        boolean mayBeContained = bloomFilter.mightContain(bigInteger.toByteArray());

//        final List<String> strings = Arrays.asList("", "");
//        Map<String, String > result = strings.stream().collect(Collectors.toMap(String::toString,Function.identity()));

        cache = CacheBuilder.newBuilder()
                .maximumSize(128 * 128 * 32) // maximum 100 records can be cached
//                .maximumSize(256) // maximum 100 records can be cached
                .concurrencyLevel(4)
//                .softValues()
                .expireAfterAccess(10, TimeUnit.SECONDS) // cache will expire after 30 minutes of access
                .build(new CacheLoader<Integer, ProcessItem>() {
                    @Override
                    public ProcessItem load(Integer tableIndex) throws Exception {
                        int max = Math.max(0, tableIndex - 1024);
                        allQuery.setHint( QueryHints.REFRESH, HintValues.TRUE );
                        final TypedQuery<ProcessEntity> cityEntityTypedQuery = allQuery.setFirstResult(max).setMaxResults(1024);
//                        final String queryString = allQuery.unwrap(Query.class).getQueryString();
                        final List<ProcessEntity> resultList =  cityEntityTypedQuery.getResultList();
                        int counter = 0;
                        for (int i = max; i < max + resultList.size(); i++) {
                            resultList.get(counter++).setIndex(max + i);
                        }
//                        IntStream.range(max , max + resultList.size() ).forEach( index -> {
//                            resultList.get(index).setIndex(tableIndex);
//                        });
                        final ConcurrentMap<Integer, ProcessItem> integerCityEntityConcurrentMap = cache.asMap();
                        ProcessItem processItem = integerCityEntityConcurrentMap.get(tableIndex);
                        if (processItem != null) {
                            return processItem;
                        }


                        ConcurrentHashMap<Integer, String> depotsMap = DepotsEntity.getDepotsMap();
                        ConcurrentHashMap<Integer, CustomerEntity> customersMap = CustomerEntity.getCustomerById();
                        ConcurrentHashMap<Integer, String> companyUsersMap = CompanyUsersEntity.getCompanyUsersMap();
                        ConcurrentHashMap<Integer, String> productsMap = ProductsEntity.getProductsMap();
                        List<ProcessItem> collect = resultList.parallelStream().map(new Function<ProcessEntity, ProcessItem>() {
                            @Override
                            public ProcessItem apply(ProcessEntity processEntity) {
                                ProcessItem processItem = new ProcessItem( langUtils );
//                                GlobalDatas.getInstance().getInjector().getInstance(ProcessItem.class);
//                                processItem.setId(processItem.getId());

                                processItem.setDepot(depotsMap.get(processEntity.getDepotId()));

                                Date actionDate = processEntity.getActionDate();
                                final ZonedDateTime zonedDateTime = actionDate.toInstant().atZone(ZoneId.systemDefault());

                                processItem.setActionDate( zonedDateTime.toLocalDate() );
                                CustomerEntity s1 = customersMap.get(processEntity.getCompanyId());
                                processItem.setCompany(s1.getTitle());
                                processItem.setDescription(processEntity.getDescription());
                                processItem.setPartNo(processEntity.getPartNo());
                                processItem.setPrice(processEntity.getPrice());
                                String s = productsMap.get(processEntity.getProductId());
                                processItem.setProduct( s );
                                processItem.setCompanyUserId(companyUsersMap.get(processEntity.getCompanyUserId()));
                                processItem.setUnits(processEntity.getUnits());
                                processItem.setPrice(processEntity.getPrice());
                                processItem.setDescription(String.valueOf(processEntity.getDescription()));
                                processItem.setIndex(processEntity.getIndex());
                                processItem.setProcessType(processEntity.getProcessType());
                                processItem.setActionTime( zonedDateTime.toLocalTime() );
                                return processItem;
                            }
                        }).collect(Collectors.toList());

                        collect.parallelStream().forEach(processItem22 -> {
                            cache.asMap().put(processItem22.getIndex(),processItem22);
                        });
                        final ProcessItem processItem3 = cache.asMap().get(tableIndex);
                        if(processItem3 != null)
                            return processItem3;
                        else
                            return null;
                    } // build the cacheloader
                });



        CriteriaQuery<Long> countCriteria = cb.createQuery(Long.class);
        Root<?> entityRoot = rootEntry;
        countCriteria.select(cb.count(countCriteria.from(ProcessEntity.class)));
//        countCriteria.where(countCriteria.getRestriction());
        final TypedQuery<Long> query = entityManager.createQuery(countCriteria);
        cacheSize = new Supplier<Long>() {
            @Override
            public Long get() {
                return query.getSingleResult();
            }
        };
        Suppliers.memoizeWithExpiration(cacheSize, 5, TimeUnit.SECONDS);
    }


    @Override
    public void addListener(ListChangeListener listener) {
        changeListener.add(listener);
    }

    @Override
    public void removeListener(ListChangeListener listener) {
        changeListener.remove(listener);
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
    public FilteredList filtered(Predicate predicate) {
        return null;
    }

    @Override
    public SortedList sorted(Comparator comparator) {
        return null;
    }

    @Override
    public SortedList sorted() {
        return null;
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
    public void forEach(Consumer action) {

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
    public boolean removeIf(Predicate filter) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return false;
    }

    @Override
    public void replaceAll(UnaryOperator operator) {

    }

    @Override
    public void sort(Comparator c) {

    }

    @Override
    public void clear() {
        cache.cleanUp();
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public Object get(int index) {
        try {
            ProcessItem processEntity = cache.get(index);
            if(processEntity == null)
                return new ProcessItem( langUtils );
//            System.out.println(city);
            return processEntity;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return "";
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
    public Spliterator spliterator() {
        return null;
    }

    @Override
    public Stream stream() {
        return null;
    }

    @Override
    public Stream parallelStream() {
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
