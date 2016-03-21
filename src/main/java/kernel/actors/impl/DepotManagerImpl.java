package kernel.actors.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import jpa.ProcessEntity;
import jpa.ProcessType;
import jpa.TotalProductsEntity;
import kernel.actors.DepotManager;
import kernel.events.ConnectionException;
import kernel.network.DBManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;

/**
 * Created by kadir.basol on 16.3.2016.
 */
@Singleton
public class DepotManagerImpl implements DepotManager {

    @Inject
    private DBManager dbManager;

    @Override
    @Transactional
    public Integer productIn(int companyId, int companyUserId, int productId, long count, int depoId, long tutar, String aciklama) throws ConnectionException {
        ProcessEntity processEntity = new ProcessEntity(productId,depoId, ProcessType.ENTRANCE,companyId,tutar,aciklama);
//        CREATE RULE range_rule AS @range>= $1000 AND @range <$20000;
//        Toplam depodaki urune ekleme yap ve lokasyonu belirt firma ismi
        final EntityManager entityManager = dbManager.get();

        entityManager.persist(processEntity);
        return processEntity.getId();
    }

    @Override
    @Transactional
    public void productOut(int partiNo, int companyId, int productId, long count, int depoId, long tutar, String aciklama) throws ConnectionException {
        ProcessEntity processEntity = new ProcessEntity(partiNo,productId,depoId, ProcessType.WAYOUT,companyId,tutar,aciklama);
        final EntityManager entityManager = dbManager.get();
        //        Toplam depodaki urune ekleme yap ve lokasyonu belirt firma ismi
        entityManager.persist(processEntity);
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
     (
     ID int,
     Value varchar(30),
     User_Input varchar(200) DEFAULT SYSTEM_USER
     )
     insert into #CheckConstraint(ID,Price) values(2,default)
     * @return
     */
    @Override
    public String getUserName() {
        final EntityManager entityManager = dbManager.get();
        final Query nativeQuery = entityManager.createNativeQuery("SELECT SYSTEM_USER");
        final String s = nativeQuery.getSingleResult().toString();
        System.out.println(s);
        return s;
    }
}
