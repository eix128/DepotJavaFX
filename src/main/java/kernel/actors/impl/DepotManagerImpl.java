package kernel.actors.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import jpa.converters.enums.PriceType;
import jpa.converters.enums.UnitType;
import kernel.actors.DepotManager;
import kernel.actors.SQLServices;
import kernel.events.ConnectionException;
import kernel.network.DBManager;
import kernel.threads.network.NetworkThread;
import org.javatuples.Pair;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import java.time.LocalDate;
import java.util.concurrent.Callable;

/**
 * Created by kadir.basol on 16.3.2016.
 */
@Singleton
public class DepotManagerImpl implements DepotManager {

    @Inject
    private DBManager dbManager;

    @Inject
    private NetworkThread networkThread;

    @Inject
    private SQLServices sqlServices;

    @Override
    public void productIn(
            StackPane activePanel , int partiNo , int companyId, int
            companyUserId, int productId, long units, UnitType unitsType, int depoId, long price ,
            PriceType priceType , String aciklama) throws ConnectionException {
//        CREATE RULE range_rule AS @range>= $1000 AND @range <$20000;
//        Toplam depodaki urune ekleme yap ve lokasyonu belirt firma ismi

        final EntityManager entityManager = dbManager.get();

        networkThread.aSyncOperation(activePanel, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("DepotManager_productIn");
                query.setParameter("Units", units);
                query.setParameter("UnitsType", unitsType.getValue());
                query.setParameter("Price", price);
                query.setParameter("PriceType", priceType.getValue());
                query.setParameter("Description", aciklama);
                query.setParameter("PartNo", partiNo);
                query.setParameter("CompanyId", companyId);
                query.setParameter("DepotId", depoId);
                query.setParameter("ProductId", productId);
                query.setParameter("CompanyUserId", companyUserId);
                query.execute();
                final Integer outputParameterValue = (Integer) query.getOutputParameterValue("Success");
                final Object message = query.getOutputParameterValue("Message");
                return new Pair<Integer, String>(outputParameterValue, message.toString());
            }
        }, new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object result) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    @Override
    public void productOut(int partiNo, int companyId , int companyUserId , int productId, long count, int depoId, long tutar, String aciklama) throws ConnectionException {
        final EntityManager entityManager = dbManager.get();
        //        Toplam depodaki urune ekleme yap ve lokasyonu belirt firma ismi
        networkThread.aSyncOperation(null, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("DepotManager_productOut");
        /*
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "CompanyId"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "DepotId"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = Integer.class, name = "ProductId"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "ProcessAmount"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "Description"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Byte.class, name = "ProcessAmountType"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "PartNo"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = Integer.class, name = "Success")
         */
                query.setParameter("CompanyId", companyId);
                query.setParameter("DepotId", depoId);
                query.setParameter("ProductId", productId);
                query.setParameter("Units", count);
                query.setParameter("UnitsType", UnitType.BOX.getValue());
                query.setParameter("Price", tutar);
                query.setParameter("PriceType", PriceType.TL.getValue());
                query.setParameter("Description", aciklama);
                query.setParameter("PartNo", partiNo);
                query.setParameter("CompanyUserId", companyUserId);
                query.execute();
                final Integer outputParameterValue = (Integer) query.getOutputParameterValue("Success");
                final Object message = query.getOutputParameterValue("Message");
                return new Pair<Integer, String>(outputParameterValue, message.toString());
            }
        }, new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object result) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public void productMove(int targetDepotId, int sourceDepotId, int partiNo, int companyId, int productId, long count, int depoId, long tutar, String aciklama) throws ConnectionException {
        final EntityManager entityManager = dbManager.get();

    }

    @Override
    public void productTransfer(int partiNo, int companyId, int productId, long count, int depoId, long tutar, String aciklama) throws ConnectionException {
        final EntityManager entityManager = dbManager.get();
    }

    @Override
    public void viewSingleProduct(int partiNo, int companyId, int productId, LocalDate startDate, LocalDate endDate) throws ConnectionException {
        final EntityManager entityManager = dbManager.get();
    }

    @Override
    public void viewProductList(int partiNo, int companyId, LocalDate startDate, LocalDate endDate) throws ConnectionException {
        final EntityManager entityManager = dbManager.get();
    }

    @Override
    public void viewProductIns(int partiNo, int companyId, int productId, LocalDate startDate, LocalDate endDate) throws ConnectionException {
        final EntityManager entityManager = dbManager.get();
    }

    @Override
    public void viewProductOuts(int partiNo, int companyId, int productId, LocalDate startDate, LocalDate endDate) throws ConnectionException {
        final EntityManager entityManager = dbManager.get();
    }

    @Override
    public void viewProductOuts(int partiNo, int companyId, LocalDate startDate, LocalDate endDate) throws ConnectionException {
        final EntityManager entityManager = dbManager.get();
    }

    @Override
    public void addCustomer(int companyId, String customerName) throws ConnectionException {
        final EntityManager entityManager = dbManager.get();
    }

    @Override
    public boolean addCompany(String companyName) throws ConnectionException {
        final EntityManager entityManager = dbManager.get();
//        totalbox ekle

        return false;
    }

    @Override
    public int queryCompanyId(String companyName) throws ConnectionException {
        final EntityManager entityManager = dbManager.get();
        return 0;
    }

    @Override
    public int queryProductId(String productName) throws ConnectionException {
        final EntityManager entityManager = dbManager.get();
        return 0;
    }

    @Override
    public int queryDepotId(String depotLocation) throws ConnectionException {
        final EntityManager entityManager = dbManager.get();
        return 0;
    }

    @Override
    public int queryCompanyUserId(String userName, String userSurname, int tcKimlikNo) throws ConnectionException {
        final EntityManager entityManager = dbManager.get();
        return 0;
    }

    /**
     * CREATE TABLE MyTable
     * (
     * ID int,
     * Value varchar(30),
     * User_Input varchar(200) DEFAULT SYSTEM_USER
     * )
     * insert into #CheckConstraint(ID,Price) values(2,default)
     *
     * @return
     */

}
