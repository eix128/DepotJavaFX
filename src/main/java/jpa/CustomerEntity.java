package jpa;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import globals.GlobalDatas;
import globals.interfaces.PostInit;
import jpa.utils.JPAUtils;
import kernel.network.DBManager;
import utils.guava.LazyCache;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kadir.basol on 21.3.2016.
 */
@Entity
@Table(name = "customer", schema = "dbo", catalog = "jdepo")
public class CustomerEntity {

    private int id;
    private String name;
    private String surname;
    private String unvan;
    private Byte type;
    private String phoneNumber;
    private String address;
    private Long tcKimlik;
    private String vergiKimlik;
    private String country;
    private String province;
    private String city;

    private int    status;


    private static LazyCache<List<CustomerEntity>> customersList;
    private static ConcurrentHashMap<Integer,String> customersMap = new ConcurrentHashMap<>( );

    public static ConcurrentHashMap<Integer, String> getCustomersMap() {
        return customersMap;
    }

    public static LazyCache<List<CustomerEntity>> getCustomersList() {
        return customersList;
    }


    @PostInit
    public static void init() {
        TypedQuery<CustomerEntity> allData = JPAUtils.getAllData(CustomerEntity.class);
        customersList = new LazyCache<List<CustomerEntity>>(new Supplier<List<CustomerEntity>>() {
            @Override
            public List<CustomerEntity> get() {
                List<CustomerEntity> resultList = allData.getResultList();
                resultList.add(0,new CustomerEntity());
                for (CustomerEntity customerEntity : resultList) {
                    if(customerEntity.getId() != -1)
                    customersMap.put( customerEntity.getId() , customerEntity.getUnvan() );
                }
                return resultList;
            }
        });
        customersList.get();
        Injector injector = GlobalDatas.getInstance().getInjector();
        DBManager instance = injector.getInstance(DBManager.class);
        instance.addRefresh(customersList);
    }


    public CustomerEntity() {
        this.name = "";
        this.surname = "";
        this.id = -1;
    }


    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
    @Column(name = "name", nullable = true, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname", nullable = true, length = 64)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "unvan", nullable = true, length = 64)
    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan;
    }

    @Basic
    @Column(name = "type", nullable = true)
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "phoneNumber", nullable = true, length = 32)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "tcKimlik", nullable = true)
    public Long getTcKimlik() {
        return tcKimlik;
    }

    public void setTcKimlik(Long tcKimlik) {
        this.tcKimlik = tcKimlik;
    }

    @Basic
    @Column(name = "city", nullable = true)
    public String getCity() {
        return city;
    }

    @Basic
    @Column(name = "province", nullable = true)
    public String getProvince() {
        return province;
    }


    @Basic
    @Column(name = "country", nullable = true)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setVergiKimlik(String vergiKimlik) {
        this.vergiKimlik = vergiKimlik;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerEntity that = (CustomerEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (unvan != null ? !unvan.equals(that.unvan) : that.unvan != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (tcKimlik != null ? !tcKimlik.equals(that.tcKimlik) : that.tcKimlik != null) return false;
        if (vergiKimlik != null ? !vergiKimlik.equals(that.vergiKimlik) : that.vergiKimlik != null) return false;

        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (unvan != null ? unvan.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (tcKimlik != null ? tcKimlik.hashCode() : 0);
        result = 31 * result + (vergiKimlik != null ? vergiKimlik.hashCode() : 0);

        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
