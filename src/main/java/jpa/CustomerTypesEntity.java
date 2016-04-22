package jpa;

import com.google.common.base.Supplier;
import com.google.inject.Injector;
import globals.GlobalDatas;
import globals.interfaces.PostInit;
import jpa.utils.JPAUtils;
import kernel.network.DBManager;

import javax.persistence.*;
import java.util.List;

/**
 * Created by kadir.basol on 21.3.2016.
 */
@Entity
@Table(name = "customerTypes", schema = "dbo", catalog = "jdepo")
public class CustomerTypesEntity {
    private static Supplier<List<CustomerTypesEntity>> customerTypes;

    private int id;
    private Integer customerType;
    private String info;


    public CustomerTypesEntity() {
    }


    public static Supplier<List<CustomerTypesEntity>> getCustomerByTypes() {
        return customerTypes;
    }


    @PostInit
    public static void init() {
        TypedQuery<CustomerTypesEntity> allData = JPAUtils.getAllData(CustomerTypesEntity.class);
        customerTypes = new Supplier<List<CustomerTypesEntity>>() {
            @Override
            public List<CustomerTypesEntity> get() {
                return allData.getResultList();
            }
        };
    }

    public CustomerTypesEntity(Integer customerType, String info) {
        this.customerType = customerType;
        this.info = info;
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
    @Column(name = "customerType", nullable = true)
    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    @Basic
    @Column(name = "info", nullable = true, length = 64)
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerTypesEntity that = (CustomerTypesEntity) o;

        if (id != that.id) return false;
        if (customerType != null ? !customerType.equals(that.customerType) : that.customerType != null) return false;
        if (info != null ? !info.equals(that.info) : that.info != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (customerType != null ? customerType.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }
}
