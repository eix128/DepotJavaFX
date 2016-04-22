package gui.jpa;

import gui.items.TotalProductsItem;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import jpa.*;
import jpa.converters.enums.ProcessType;
import language.LangUtils;
import org.javatuples.Triplet;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Kadir on 4/9/2016.
 */
public class DynamicJPAFilteredDepots implements ObservableList {

    private LangUtils langUtils;
    private EntityManager entityManager;
    private List<TotalProductsItem> processItems;
    private DepotsEntity depotsEntity;
    private ProductsEntity productsEntity;
    private CustomerEntity customerEntity;


    public DynamicJPAFilteredDepots(LangUtils langUtils, EntityManager entityManager) {
        this.langUtils = langUtils;
        this.entityManager = entityManager;
        processItems = new ArrayList<>( );
    }



    public void filter(DepotsEntity depotsEntity, ProductsEntity productsEntity, CustomerEntity customerEntity) {
        this.depotsEntity = depotsEntity;
        this.productsEntity = productsEntity;
        this.customerEntity = customerEntity;

        final EntityManager entityManager = this.entityManager;
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<TotalProductsEntity> query = criteriaBuilder.createQuery(TotalProductsEntity.class);

        final Root<TotalProductsEntity> rootEntry = query.from(TotalProductsEntity.class);

//        Expression<String> postgresqlCastFunction = cb.function("CAST", String.class, from.<String>get("ID").as(String.class));
//        Predicate<String> p = cb.like(postgresqlCastFunction, "10%");

        CriteriaQuery<TotalProductsEntity> all = query.select(rootEntry);
        final javax.persistence.criteria.Path<Integer> pathDepotId = rootEntry.get("depotId");
        final javax.persistence.criteria.Path<Integer> pathCompanyId = rootEntry.get("companyId");
        final javax.persistence.criteria.Path<Integer> pathProductId = rootEntry.get("productId");

        ArrayList<Triplet<Expression, ? , Predicate>> arrayList = new ArrayList<Triplet<Expression, ? , Predicate>>();
        if (depotsEntity != null && depotsEntity.getId() != -1) {
            final Predicate equal = criteriaBuilder.equal(pathDepotId, depotsEntity.getId());
            arrayList.add(new Triplet<>(pathDepotId, depotsEntity.getDepotId(), equal));
        }
        if (customerEntity != null && customerEntity.getId() != -1) {
            final Predicate equal2 = criteriaBuilder.equal(pathCompanyId, customerEntity.getId());
            arrayList.add(new Triplet<>(pathCompanyId, customerEntity.getId(), equal2));
        }

        if (productsEntity != null && productsEntity.getId() != -1) {
            final Predicate equal3 = criteriaBuilder.equal(pathProductId, productsEntity.getId());
            arrayList.add(new Triplet<>(pathProductId, productsEntity.getId(), equal3));
        }

        if (arrayList.size() == 0) {
            return;
        }


        Expression expression = null;
        for (int i = 0; i < arrayList.size(); i++) {
            final Triplet<Expression, ? , Predicate> objects = arrayList.get(i);
            final Expression value0 = objects.getValue0();
            Object value1 = objects.getValue1();

            if (expression != null)
                expression = criteriaBuilder.and(criteriaBuilder.equal(value0, value1), expression);
            else
                expression = criteriaBuilder.equal(value0, value1);
        }
//        final Predicate and = criteriaBuilder.and(equal, equal1);
//        final Predicate and1 = criteriaBuilder.and(and, equal2);
//
//        final Predicate and2 = criteriaBuilder.and(and1, equal3);

        final CriteriaQuery<TotalProductsEntity> where = all.where(expression);
        final TypedQuery<TotalProductsEntity> query1 = entityManager.createQuery(where);
        final List<TotalProductsEntity> resultList = query1.getResultList();
        final List<TotalProductsItem> collect = resultList.parallelStream().map(new Function<TotalProductsEntity, TotalProductsItem>() {
            @Override
            public TotalProductsItem apply(TotalProductsEntity TotalProductsEntity) {
                final TotalProductsItem from = TotalProductsItem.from(langUtils,TotalProductsEntity);
                return from;
            }
        }).collect(Collectors.toList());
        this.processItems = collect;
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
        return processItems.size();
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

        filter(depotsEntity, productsEntity, customerEntity);
    }

    @Override
    public Object get(int index) {
        return processItems.get(index);
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
