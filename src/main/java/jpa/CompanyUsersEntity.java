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
import javax.persistence.criteria.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kadir.basol on 21.3.2016.
 */
@Entity
@Table(name = "companyUsers", schema = "dbo", catalog = "jdepo")
public class CompanyUsersEntity implements SettableString {

    private static LazyCache<List<CompanyUsersEntity>> companyUsers;

    private String userName;
    private String userSurname;

    @Id
    @Column(name = "id")
    private int     id;

    @Column(name = "tcIdentityNo")
    private Long tcIdentityNo;

    @Column(name = "userEmail",length = 64)
    private String userEmail;

    @Column(name = "userPhone",length = 64)
    private String userPhone;

    @Column(name = "status")
    private Byte status;

    @Column(name = "companyid")
    private Integer companyId;


    private static final ConcurrentHashMap<Integer,String> companyUsersMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String,CompanyUsersEntity> companyUsersByName = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Integer, String> getCompanyUsersMap() {
        return companyUsersMap;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public CompanyUsersEntity() {

    }


    public static ConcurrentHashMap<String, CompanyUsersEntity> getCompanyUsersByName() {
        return companyUsersByName;
    }

    @PostInit
    public static final void init() {
        TypedQuery<CompanyUsersEntity> allData = JPAUtils.getAllData(CompanyUsersEntity.class);
        List<CompanyUsersEntity> resultList = allData.getResultList();
        companyUsersMap.clear();
        for (CompanyUsersEntity companyUsersEntity : resultList) {
            companyUsersMap.put(companyUsersEntity.getId(),companyUsersEntity.getUserName() + companyUsersEntity.getUserSurname() );
            companyUsersByName.put(companyUsersEntity.getUserName() + ' ' + companyUsersEntity.getUserSurname(),companyUsersEntity.getValue());
        }
    }


    public static final List<CompanyUsersEntity> getListByCompany(int companyId) {
        Injector injector = GlobalDatas.getInstance().getInjector();
        DBManager instance = injector.getInstance(DBManager.class);
        final CriteriaBuilder criteriaBuilder = instance.getCriteriaBuilder();
        final CriteriaQuery<CompanyUsersEntity> query = criteriaBuilder.createQuery(CompanyUsersEntity.class);
        final Root<CompanyUsersEntity> from = query.from(CompanyUsersEntity.class);
        final CriteriaQuery<CompanyUsersEntity> select = query.select(from);
        final Path<Integer> objectPath = from.get("companyId");
        final Path<Integer> objectPath2 = from.get("status");
//        ParameterExpression<Integer> p = criteriaBuilder.parameter(Integer.class);
        Predicate and = criteriaBuilder.and(criteriaBuilder.equal(objectPath, companyId), criteriaBuilder.notEqual(objectPath2, 0));
        CriteriaQuery<CompanyUsersEntity> where = select.where(and);
        final TypedQuery<CompanyUsersEntity> query1 = instance.get().createQuery(where);
        return query1.getResultList();
    }

//    @PostInit
//    public static void init() {
//        Injector injector = GlobalDatas.getInstance().getInjector();
//        DBManager instance = injector.getInstance(DBManager.class);
//        final CriteriaBuilder criteriaBuilder = instance.getCriteriaBuilder();
//        final CriteriaQuery<CompanyUsersEntity> query = criteriaBuilder.createQuery(CompanyUsersEntity.class);
//        final Root<CompanyUsersEntity> from = query.from(CompanyUsersEntity.class);
//        final CriteriaQuery<CompanyUsersEntity> select = query.select(from);
//        final Path<Integer> objectPath = from.get("");
//        select.where( criteriaBuilder.equal(objectPath,id) );
//        final TypedQuery<CompanyUsersEntity> query1 = instance.get().createQuery(select);
//        query1.getResultList();
//
//
//        instance.addRefresh(companyUsers);
//    }


    public CompanyUsersEntity(String userName, String userSurname, String userEmail) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userEmail = userEmail;
    }

    @Basic
    @Column(name = "userEmail",unique = false)
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Basic
    @Column(name = "userName", nullable = false, length = 64)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "userSurname", nullable = false, length = 64)
    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public Long getTcIdentityNo() {
        return tcIdentityNo;
    }
    public void setTcIdentityNo(Long tcIdentityNo) {
        this.tcIdentityNo = tcIdentityNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyUsersEntity that = (CompanyUsersEntity) o;

        if (tcIdentityNo != that.tcIdentityNo) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (userSurname != null ? !userSurname.equals(that.userSurname) : that.userSurname != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }


    @Override
    public String toString() {
        return  userName + ' '  + userSurname;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    @Override
    public String getString() {
        return userName + ' ' + userSurname;
    }

    @Override
    public CompanyUsersEntity getValue() {
        return CompanyUsersEntity.this;
    }

}
