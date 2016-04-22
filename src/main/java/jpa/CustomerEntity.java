package jpa;

import com.google.common.base.Supplier;
import com.google.inject.Injector;
import globals.GlobalDatas;
import globals.interfaces.PostInit;
import javafx.scene.control.ComboBox;
import jpa.converters.CustomerTypeConverter;
import jpa.converters.enums.CustomerType;
import jpa.utils.JPAUtils;
import kernel.network.DBManager;
import utils.guava.LazyCache;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kadir.basol on 21.3.2016.
 */
@Entity
@Table(name = "customer", schema = "dbo", catalog = "jdepo")
public class CustomerEntity implements SettableString {

    private int id;
    private String name;
    private String surname;
    private String title;
    private CustomerType type;
    private String phoneNumber;
    private String address;
    private Long tcKimlik;
    private String vergiKimlik;
    private String country;
    private String province;
    private String city;

    private int    status;


    private static LazyCache<List<CustomerEntity>> customersList;
    private static ConcurrentHashMap<Integer,CustomerEntity> customersMapById = new ConcurrentHashMap<>( );
    private static ConcurrentHashMap<String,CustomerEntity> customersMapByTitle = new ConcurrentHashMap<>( );
    private static ConcurrentHashMap<CustomerType,List<CustomerEntity>> customersMapByCompanyType = new ConcurrentHashMap<>( );


    private static LazyCache<List<CustomerEntity>> customerByType;
    private ComboBox comboBox;

    public static ConcurrentHashMap<Integer, CustomerEntity > getCustomerById() {
        return customersMapById;
    }


    public static ConcurrentHashMap<CustomerType, List<CustomerEntity>> getCustomersMapByCompanyType() {
        return customersMapByCompanyType;
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
                List<CustomerEntity> resultList = null;
                try {
                    resultList = allData.getResultList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                resultList.add(0,new CustomerEntity());
                customersMapByTitle.clear();
                customersMapByCompanyType.clear();
                for (CustomerEntity customerEntity : resultList) {
                    if(customerEntity.getId() != -1) {
                        customersMapById.put(customerEntity.getId(), customerEntity);
                        customersMapByTitle.put(customerEntity.getTitle(),customerEntity);
                        final List<CustomerEntity> customerEntity1 = customersMapByCompanyType.get(customerEntity.getType());
                        if(customerEntity1 == null) {
                            ArrayList<CustomerEntity> list = new ArrayList( );
                            list.add(customerEntity);
                            customersMapByCompanyType.put(customerEntity.getType(),list);
                        } else {
                            customerEntity1.add(customerEntity);
                        }
                    }
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
        ResourceBundle instance = GlobalDatas.getInstance().getInjector().getInstance(ResourceBundle.class);
        this.title = instance.getString("mainPanel.all");
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
    @Column(name = "title", nullable = false, length = 64)
    public String getTitle() {
        return title;
    }

    public void setTitle(String unvan) {
        this.title = unvan;
    }


    public void setType(CustomerType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "type" , columnDefinition = "TINYINT")
    @Convert(converter = CustomerTypeConverter.class )
    public CustomerType getType() {
        return type;
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

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        return result;
    }

    @Override
    public String toString() {
        return title;
    }


    @Override
    public void setString(String data) {
        comboBox.setValue(this);
    }

    @Transient
    @Override
    public String getString() {
        return title;
    }

    @Transient
    @Override
    public CustomerEntity getValue() {
        return this;
    }

    public static CustomerEntity getCustomersByTitle(String string) {
        return customersMapByTitle.get(string);
    }
}
