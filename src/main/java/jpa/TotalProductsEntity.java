package jpa;

import com.google.common.base.Supplier;
import globals.interfaces.PostInit;
import jpa.utils.JPAUtils;
import utils.guava.LazyCache;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by kadir.basol on 21.3.2016.
 */
@Entity
@Table(name = "totalProducts", schema = "dbo", catalog = "jdepo")
public class TotalProductsEntity {

    private static LazyCache<List<TotalProductsEntity>> depotNames;

    private int productId;
    private Integer companyId;
    private Integer depotId;
    private Long units;
    private Byte unitType;
    private int id;
    private Long partiNo;
    private Date dateAdded;
    private int index;


    public static LazyCache<List<TotalProductsEntity>> getTotalProducts() {
        return depotNames;
    }


    public TotalProductsEntity(int productId, Integer companyId, Integer depotId, Long units) {
        this.productId = productId;
        this.companyId = companyId;
        this.depotId = depotId;
        this.units = units;
    }

    public TotalProductsEntity() {

    }

    @PostInit
    public static void init() {
        final TypedQuery<TotalProductsEntity> allData = JPAUtils.getAllData(TotalProductsEntity.class);
        depotNames = new LazyCache<List<TotalProductsEntity>>(new Supplier<List<TotalProductsEntity>>() {
            @Override
            public List<TotalProductsEntity> get() {
                List<TotalProductsEntity> resultList = allData.getResultList();
//                resultList.add(0,new TotalProductsEntity());
                return resultList;
            }
        });
    }

    @Basic
    @Column(name = "productid", nullable = false)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "companyid", nullable = false)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "depotid", nullable = false)
    public Integer getDepotId() {
        return depotId;
    }

    public void setDepotId(Integer depotId) {
        this.depotId = depotId;
    }

    @Basic
    @Column(name = "units", nullable = false)
    public Long getUnits() {
        return units;
    }

    public void setUnits(Long totalBox) {
        this.units = totalBox;
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
    @Column(name = "unitType")
    public Byte getUnitType() {
        return unitType;
    }

    public void setUnitType(Byte unitType) {
        this.unitType = unitType;
    }


    @Column(name = "partiNo" ,columnDefinition = "BIGINT")
    public Long getPartiNo() {
        return partiNo;
    }

    public void setPartiNo(Long partNo) {
        this.partiNo = partNo;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TotalProductsEntity that = (TotalProductsEntity) o;

        if (productId != that.productId) return false;
        if (id != that.id) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (depotId != null ? !depotId.equals(that.depotId) : that.depotId != null) return false;
        if (units != null ? !units.equals(that.units) : that.units != null) return false;
        if (unitType != null ? !unitType.equals(that.unitType) : that.unitType != null) return false;
        if (partiNo != null ? !partiNo.equals(that.partiNo) : that.partiNo != null) return false;
        return dateAdded != null ? dateAdded.equals(that.dateAdded) : that.dateAdded == null;

    }

    @Override
    public int hashCode() {
        int result = productId;
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (depotId != null ? depotId.hashCode() : 0);
        result = 31 * result + (units != null ? units.hashCode() : 0);
        result = 31 * result + (unitType != null ? unitType.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + (partiNo != null ? partiNo.hashCode() : 0);
        result = 31 * result + (dateAdded != null ? dateAdded.hashCode() : 0);
        return result;
    }

    @Transient
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
