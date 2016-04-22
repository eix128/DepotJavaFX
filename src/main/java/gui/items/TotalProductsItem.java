package gui.items;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import jpa.*;
import language.LangUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kadir.basol on 5.4.2016.
 */
public class TotalProductsItem implements Comparable<TotalProductsItem> {

    private Long               id;
    private SimpleLongProperty partiNo;
    private SimpleStringProperty customerName;
    private SimpleStringProperty productName;
    private SimpleStringProperty depotName;
    private SimpleLongProperty mevcut;

    private Integer index;

    public long getPartiNo() {
        return partiNo.get();
    }

    public SimpleLongProperty partiNoProperty() {
        return partiNo;
    }

    public void setPartiNo(long partiNo) {
        this.partiNo.set(partiNo);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public String getDepotName() {
        return depotName.get();
    }

    public SimpleStringProperty depotNameProperty() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName.set(depotName);
    }

    public long getMevcut() {
        return mevcut.get();
    }

    public SimpleLongProperty mevcutProperty() {
        return mevcut;
    }

    public void setMevcut(long mevcut) {
        this.mevcut.set(mevcut);
    }

    public TotalProductsItem() {
        customerName = new SimpleStringProperty( );
        productName = new SimpleStringProperty( );
        depotName = new SimpleStringProperty( );
        id = new Long( 0 );
        partiNo = new SimpleLongProperty( );
        mevcut = new SimpleLongProperty( );
    }

    public static final TotalProductsItem from(LangUtils langUtils, TotalProductsEntity totalProductsEntity) {

        ConcurrentHashMap<Integer, String> depotsMap = DepotsEntity.getDepotsMap();
        ConcurrentHashMap<Integer, CustomerEntity> customersMap = CustomerEntity.getCustomerById();
        ConcurrentHashMap<Integer, String> companyUsersMap = CompanyUsersEntity.getCompanyUsersMap();
        ConcurrentHashMap<Integer, String> productsMap = ProductsEntity.getProductsMap();

        TotalProductsItem totalProductsItem = new TotalProductsItem();
        final CustomerEntity customerEntity = customersMap.get(totalProductsEntity.getCompanyId());


        totalProductsItem.partiNo.set( totalProductsEntity.getPartiNo() );
        totalProductsItem.customerName.set( customersMap.get( totalProductsEntity.getCompanyId()  ).getTitle() );
        totalProductsItem.productName.set( productsMap.get( totalProductsEntity.getProductId() ) );
        totalProductsItem.depotName.set( depotsMap.get( totalProductsEntity.getDepotId() ) );
        totalProductsItem.mevcut.set( totalProductsEntity.getUnits() );

        totalProductsItem.setIndex( totalProductsEntity.getIndex() );
        totalProductsItem.setId( Long.valueOf(totalProductsEntity.getId()) );
        return totalProductsItem;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public int compareTo(TotalProductsItem o) {
        return getIndex().compareTo(o.getIndex());
    }
}
