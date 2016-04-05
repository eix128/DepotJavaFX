package jpa;

import com.google.common.base.Supplier;
import com.google.inject.Injector;
import globals.GlobalDatas;
import globals.interfaces.PostInit;
import jpa.listeners.DepotsEntityListener;
import jpa.utils.JPAUtils;
import kernel.network.DBManager;
import utils.guava.LazyCache;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kadir.basol on 21.3.2016.
 */
@Entity
@Table(name = "depots", schema = "dbo", catalog = "jdepo")
@EntityListeners(
        DepotsEntityListener.class
)
/*
  @CompanyId INT ,
  @DepotId  INT,
  @ProductId INT,
  @ProcessAmount BIGINT,
  @Description TEXT,
  @ProcessAmountType TINYINT,
  @PartNo INT,
  @Success INT OUT
 */
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "DepotManager_productIn",
                procedureName = "DepotManager_productIn",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "CompanyId"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "DepotId"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "ProductId"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "Units"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Byte.class, name = "UnitsType"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "Price"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Byte.class, name = "PriceType"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "Description"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "PartNo"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "CompanyUserId"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = Integer.class, name = "Success"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "Exception")
                }
        ),
        @NamedStoredProcedureQuery(
                name = "DepotManager_productOut",
                procedureName = "DepotManager_productOut",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "CompanyId"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "DepotId"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "ProductId"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "Units"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Byte.class, name = "UnitsType"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "Price"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Byte.class, name = "PriceType"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "PartNo"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "CompanyUserId"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "Description"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = Integer.class, name = "Success"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "Exception")
                }
        )
})
public class DepotsEntity {
    private int id;
    private Integer depotId;
    private String depotName;

    public static LazyCache<List<DepotsEntity>> depotNames;
    public static ConcurrentHashMap<Integer, String> depotsMap = new ConcurrentHashMap<Integer, String>();


    public static LazyCache<List<DepotsEntity>> getDepotNames() {
        return depotNames;
    }

    public static ConcurrentHashMap<Integer, String> getDepotsMap() {
        return depotsMap;
    }

    public DepotsEntity() {
        this.depotName = "";
        this.id = -1;
    }

    public DepotsEntity(Integer depotId, String depotName) {
        this.depotId = depotId;
        this.depotName = depotName;
    }


    @PostInit
    public static void init() {
        TypedQuery<DepotsEntity> allData = JPAUtils.getAllData(DepotsEntity.class);
        depotNames = new LazyCache<List<DepotsEntity>>(new Supplier<List<DepotsEntity>>() {
            @Override
            public List<DepotsEntity> get() {
                depotsMap.clear();
                List<DepotsEntity> resultList = allData.getResultList();
                resultList.add(0,new DepotsEntity());
                for (DepotsEntity depotsEntity : resultList) {
                    depotsMap.put(depotsEntity.getId(), depotsEntity.getDepotName());
                }
                return resultList;
            }
        });

        Injector injector = GlobalDatas.getInstance().getInjector();
        DBManager instance = injector.getInstance(DBManager.class);
        instance.addRefresh(depotNames);
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
    @Column(name = "depotid", nullable = true)
    public Integer getDepotId() {
        return depotId;
    }

    public void setDepotId(Integer depotId) {
        this.depotId = depotId;
    }

    @Basic
    @Column(name = "depotName", nullable = true, length = 64)
    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepotsEntity that = (DepotsEntity) o;

        if (id != that.id) return false;
        if (depotId != null ? !depotId.equals(that.depotId) : that.depotId != null) return false;
        if (depotName != null ? !depotName.equals(that.depotName) : that.depotName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (depotId != null ? depotId.hashCode() : 0);
        result = 31 * result + (depotName != null ? depotName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return depotName;
    }
}
