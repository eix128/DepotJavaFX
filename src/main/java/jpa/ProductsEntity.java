package jpa;

import com.google.common.base.Supplier;
import com.google.inject.Injector;
import globals.GlobalDatas;
import globals.interfaces.PostInit;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import jpa.converters.enums.ProcessType;
import jpa.utils.JPAUtils;
import kernel.network.DBManager;
import main.gui.ComboList;
import utils.guava.LazyCache;

import javax.persistence.*;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kadir.basol on 21.3.2016.
 */
@Entity
@Table(name = "products", schema = "dbo", catalog = "jdepo")
public class ProductsEntity implements SettableString {

    private int id;
    private String productName;

    private Byte   unitType;
    private String description;
    private Float  avgWeight;


    private static ConcurrentHashMap<Integer,String> productsMap = new ConcurrentHashMap<>( );
    private static ConcurrentHashMap<String,ProductsEntity> productsMapByName = new ConcurrentHashMap<>( );
    private ComboBox comboBox;


    public ProductsEntity() {
        ResourceBundle instance = GlobalDatas.getInstance().getInjector().getInstance(ResourceBundle.class);
        this.productName = instance.getString("mainPanel.all");
        this.id = -1;
    }


    public static ConcurrentHashMap<Integer, String> getProductsMap() {
        return productsMap;
    }

    private static LazyCache<List<ProductsEntity>> productsList;

    public static LazyCache<List<ProductsEntity>> getProductsList() {
        return productsList;
    }

    public static ConcurrentHashMap<String, ProductsEntity> getProductsMapByName() {
        return productsMapByName;
    }

    @PostInit
    public static void init() {
        TypedQuery<ProductsEntity> allData = JPAUtils.getAllData(ProductsEntity.class);
        productsList = new LazyCache<List<ProductsEntity>>(new Supplier<List<ProductsEntity>>() {
            @Override
            public List<ProductsEntity> get() {
                productsMap.clear();
                productsMapByName.clear();
                final List<ProductsEntity> resultList = allData.getResultList();
                resultList.parallelStream().forEach( action -> {
                    productsMap.put( action.getId() , action.getProductName() );
                    productsMapByName.put( action.getProductName() , action );
                });
                return resultList;
            }
        });
        productsList.get();
        Injector injector = GlobalDatas.getInstance().getInjector();
        DBManager instance = injector.getInstance(DBManager.class);
        instance.addRefresh(productsList);
    }


    public ProductsEntity(String productName) {
        this.productName = productName;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "productName", nullable = true, length = 64)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "unitType", nullable = true)
    public Byte getUnitType() {
        return unitType;
    }

    public void setUnitType(Byte unitType) {
        this.unitType = unitType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductsEntity that = (ProductsEntity) o;

        if (id != that.id) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        return result;
    }


    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "avgWeight")
    public Float getAvgWeight() {
        return avgWeight;
    }

    public void setAvgWeight(Float avgWeight) {
        this.avgWeight = avgWeight;
    }

    @Override
    public String toString() {
        return productName;
    }

    @Override
    public void setString(String data) {
        ComboList instance1 = GlobalDatas.getInstance().getInjector().getInstance(ComboList.class);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                instance1.getByName("Main.transactionType").setValue(this);
            }
        });
    }

    @Transient
    @Override
    public String getString() {
        return productName;
    }

    @Transient
    @Override
    public ProductsEntity getValue() {
        return this;
    }


}
