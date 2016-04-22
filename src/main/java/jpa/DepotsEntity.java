package jpa;

import com.google.common.base.Supplier;
import com.google.inject.Injector;
import globals.GlobalDatas;
import globals.interfaces.PostInit;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import jpa.listeners.DepotsEntityListener;
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

public class DepotsEntity implements SettableString {
    private int id;
    private Integer depotId;
    private String depotName;

    private static LazyCache<List<DepotsEntity>> depotNames;
    private static ConcurrentHashMap<Integer, String> depotsMap = new ConcurrentHashMap<Integer, String>();
    private static ConcurrentHashMap<String, DepotsEntity> depotsByName = new ConcurrentHashMap<>();
    private ComboBox comboBox;


    public static LazyCache<List<DepotsEntity>> getDepotNames() {
        return depotNames;
    }


    public static ConcurrentHashMap<String, DepotsEntity> getDepotsByNameMap() {
        return depotsByName;
    }

    public static ConcurrentHashMap<Integer, String> getDepotsMap() {
        return depotsMap;
    }

    public DepotsEntity() {
        ResourceBundle instance = GlobalDatas.getInstance().getInjector().getInstance(ResourceBundle.class);
        this.depotName = instance.getString("mainPanel.all");
        this.id = -1;
    }

    public DepotsEntity(Integer depotId, String depotName) {
        this.depotId = depotId;

        if(depotName == null || depotName.trim().equals("")) {
            Injector injector = GlobalDatas.getInstance().getInjector();
            ResourceBundle instance = injector.getInstance(ResourceBundle.class);
            this.depotName = instance.getString("mainPanel.all");
        } else this.depotName = depotName;
    }


    @PostInit
    public static void init() {
        TypedQuery<DepotsEntity> allData = JPAUtils.getAllData(DepotsEntity.class);
        depotNames = new LazyCache<List<DepotsEntity>>(new Supplier<List<DepotsEntity>>() {
            @Override
            public List<DepotsEntity> get() {
                depotsMap.clear();
                depotsByName.clear();
                List<DepotsEntity> resultList = allData.getResultList();
                for (DepotsEntity depotsEntity : resultList) {
                    depotsMap.put(depotsEntity.getId(), depotsEntity.getDepotName());
                    depotsByName.put(depotsEntity.getDepotName(),depotsEntity);
                }
                return resultList;
            }
        });
        depotNames.get();
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
        return depotName;
    }

    @Transient
    @Override
    public DepotsEntity getValue() {
        return this;
    }

}
