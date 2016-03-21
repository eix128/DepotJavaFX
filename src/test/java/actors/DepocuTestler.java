package actors;

import kernel.events.ConnectionException;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by kadir.basol on 18.3.2016.
 */
public class DepocuTestler {

    /**
     * Depoya ürün girişi yapan method
     *
     * @param companyId     Ürün girişi yapan şirket id si queryCompanyId den elde edilebilir
     * @param companyUserId Ürün girişi yapan
     * @param productId     Ürün id si
     * @param count         Girilecek olan ürünün kasa sayısı
     * @param depoId        Ürün girilecek olan depo no
     * @param tutar         eklenecek olan mal tutarı
     * @param aciklama      yetkili açıklaması
     * @return Parti Numarasını verir , başarısız durumda parti no -1 gelir
     * @throws ConnectionException if database connection fails during query
     */
    public void productIn(int companyId, int companyUserId, int productId, long count, int depoId, long tutar, String aciklama) throws ConnectionException {

    }

    public void productOut(int partiNo, int companyId, int productId, long count, int depoId, long tutar, String aciklama) throws ConnectionException {

    }

    public void productMove(int targetDepotId, int sourceDepotId, int partiNo, int companyId, int productId, long count, int depoId, long tutar, String aciklama) throws ConnectionException {

    }

    public void productTransfer(int partiNo, int companyId, int productId, long count, int depoId, long tutar, String aciklama) throws ConnectionException {

    }

    public void viewSingleProduct(int partiNo, int companyId, int productId, LocalDate startDate, LocalDate endDate) throws ConnectionException {

    }

    /**
     * Firmanın bütün verilerini listeler
     *
     * @param partiNo
     * @param companyId
     * @param startDate
     * @param endDate
     * @throws ConnectionException
     */
    public void viewProductList(int partiNo, int companyId, LocalDate startDate, LocalDate endDate) throws ConnectionException {

    }

    /**
     * Firmanın seçilen balık türüne göre listeler
     *
     * @param partiNo
     * @param companyId
     * @param productId
     * @param startDate
     * @param endDate
     * @throws ConnectionException
     */
    public void viewProductIns(int partiNo, int companyId, int productId, LocalDate startDate, LocalDate endDate) throws ConnectionException {

    }


    public void viewProductOuts(int partiNo, int companyId, int productId, LocalDate startDate, LocalDate endDate) throws ConnectionException {

    }

    public void viewProductOuts(int partiNo, int companyId, LocalDate startDate, LocalDate endDate) throws ConnectionException {

    }

//    public void changeSelfPassword(String oldPassword , String newPassword ) throws ConnectionException{}

    public void addCustomer(int companyId, String customerName) throws ConnectionException {

    }

    public void addCompany(String companyName) throws ConnectionException {
    }

    public void queryCompanyId(String companyName) throws ConnectionException {

    }

    public void queryProductId(String productName) throws ConnectionException {

    }

    public void queryDepotId(String depotLocation) throws ConnectionException {
    }

    public void queryCompanyUserId(String userName, String userSurname, int tcKimlikNo) throws ConnectionException {

    }
}
