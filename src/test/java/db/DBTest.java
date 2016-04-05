package db;

import com.google.inject.Guice;
import com.google.inject.Injector;
import globals.GlobalDatas;
import jpa.CustomerEntity;
import jpa.DepotsEntity;
import jpa.converters.enums.PriceType;
import jpa.converters.enums.UnitType;
import kernel.actors.DepotManager;
import kernel.actors.SQLServices;
import kernel.events.ConnectionException;
import kernel.network.DBManager;
import main.AgentFinderModule;
import org.junit.Assert;
import org.junit.Test;
import utils.guava.LazyCache;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kadir.basol on 17.3.2016.
 */
public class DBTest {

    @Test
    public void denemeNTLM() throws ConnectionException {
        String canonicalPath = null;
        final Path path = FileSystems.getDefault().getPath(".");
        try {
            canonicalPath = path.toFile().getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        CtzJniUtils.loadJniLibsFromStandardPath(canonicalPath , "sqljdbc_auth");

        final Injector value = Guice.createInjector(new AgentFinderModule());
        GlobalDatas.getInstance().setInjector(value);
        final DBManager dbManager = value.getInstance(DBManager.class);
        final boolean b = dbManager.connectNTLM();
        Assert.assertEquals(b, true);
    }

    @Test
    public void denemePassword() throws ConnectionException {
        final Injector value = Guice.createInjector(new AgentFinderModule());
        GlobalDatas.getInstance().setInjector(value);
        final DBManager dbManager = value.getInstance(DBManager.class);
        final boolean sa = dbManager.connect("sa", "Depo@2015");
        Assert.assertEquals(sa, true);
    }


    @Test
    public void denemePassword2() throws ConnectionException {
        final Injector value = Guice.createInjector(new AgentFinderModule());
        GlobalDatas.getInstance().setInjector(value);

        final DBManager dbManager = value.getInstance(DBManager.class);
        final boolean sa = dbManager.connect("sa", "aaaa");
        Assert.assertEquals(sa, false);
    }


    @Test
    public void testDBTable() throws ConnectionException {
        final Injector value = Guice.createInjector(new AgentFinderModule());
        GlobalDatas.getInstance().setInjector(value);
        final DBManager dbManager = value.getInstance(DBManager.class);
        final DepotManager depotManager = value.getInstance(DepotManager.class);
        final SQLServices instance = value.getInstance(SQLServices.class);
        final boolean sa = dbManager.connectNTLM();
        final String userName = instance.getUserName();
        Logger.getAnonymousLogger().log(Level.INFO, userName);
        final boolean ozıdas = userName.contains("OZIDAS");
        Assert.assertEquals(ozıdas, true);
    }


    @Test
    public void testDBTime() throws ConnectionException {
        final Injector value = Guice.createInjector(new AgentFinderModule());
        GlobalDatas.getInstance().setInjector(value);
        final DBManager dbManager = value.getInstance(DBManager.class);
        final DepotManager depotManager = value.getInstance(DepotManager.class);
        final SQLServices instance = value.getInstance(SQLServices.class);
        final boolean sa = dbManager.connectNTLM();
        Assert.assertEquals(sa, true);
        final int i1 = instance.generatePartNo();
        System.out.println(i1);
    }



    @Test
    public void testStoredProcedure() throws ConnectionException {
        final Injector value = Guice.createInjector(new AgentFinderModule());
        GlobalDatas.getInstance().setInjector(value);
        final DBManager dbManager = value.getInstance(DBManager.class);
        final DepotManager depotManager = value.getInstance(DepotManager.class);
        final SQLServices instance = value.getInstance(SQLServices.class);
        final boolean sa = dbManager.connectNTLM();
        Assert.assertEquals(sa, true);
        final int i1 = instance.generatePartNo();
        System.out.println(i1);

        final EntityManager entityManager = dbManager.get();
        DepotsEntity.init();
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("DepotManager_productIn");
        /*
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = ""),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = ""),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = ""),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = ""),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Byte.class, name = ""),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = ""),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Byte.class, name = ""),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = ""),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = ""),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = ""),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = Integer.class, name = ),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "")
         */
        query.setParameter("Units", 10);
        query.setParameter("UnitsType", UnitType.BOX.getValue() );
        query.setParameter("Price", 10 );
        query.setParameter("PriceType", PriceType.TL.getValue() );
        query.setParameter("Description", "Deneme 1 2 3" );
        query.setParameter("PartNo", 45L );
        query.setParameter("CompanyId",1);
        query.setParameter("DepotId",1);
        query.setParameter("ProductId",1);
        query.setParameter("CompanyUserId", 1 );

        query.execute();
        Integer sum = (Integer) query.getOutputParameterValue("Success");
        String exception1 = (String) query.getOutputParameterValue("Exception");
        System.out.println(exception1);
        final int i = (int) (Math.random() * Integer.MAX_VALUE);
        query.setParameter("Units", 31L );
        query.setParameter("ProductId", 2 );
        query.execute();
        query.setParameter("ProductId",1);
        query.setParameter("Units", 31L );
        query.execute();
        query.setParameter("Units", 21L );
        query.setParameter("PartNo", i1 );
        query.setParameter("UnitsType", UnitType.BOX.getValue() );
        query.execute();
        sum = (Integer) query.getOutputParameterValue("Success");
        final Object exception = query.getOutputParameterValue("Exception");
        System.out.println(sum);
        System.out.println(exception);



        {
            query = entityManager.createNamedStoredProcedureQuery("DepotManager_productOut");
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
            query.setParameter("CompanyId", 1);
            query.setParameter("DepotId", 1 );
            query.setParameter("ProductId", 1 );
            query.setParameter("Price", 1 );
            query.setParameter("PriceType", PriceType.TL.getValue() );
            query.setParameter("Units", 45L );
            query.setParameter("UnitsType", UnitType.BOX.getValue() );
            query.setParameter("Description", "Deneme 1 2 3");
            query.setParameter("PartNo", instance.generatePartNo( ) );
            query.setParameter("CompanyUserId", 9999 );
            query.execute();
            sum = (Integer) query.getOutputParameterValue("Success");
            String value1 = (String) query.getOutputParameterValue("Exception");
            System.out.println(sum);
            System.out.println(value1);

            query.setParameter("Units", 21L );
            query.setParameter("PartNo", i );
            query.execute();
            sum = (Integer) query.getOutputParameterValue("Success");
            String value2 = (String) query.getOutputParameterValue("Exception");
            System.out.println(value2);

            System.out.println(sum);
        }
    }





    @Test
    public void fetchCustomers() throws ConnectionException {
        final Injector value = Guice.createInjector(new AgentFinderModule());
        GlobalDatas.getInstance().setInjector(value);
        final DBManager dbManager = value.getInstance(DBManager.class);
        final DepotManager depotManager = value.getInstance(DepotManager.class);
        final SQLServices instance = value.getInstance(SQLServices.class);
        final boolean sa = dbManager.connectNTLM();
        Assert.assertEquals(sa, true);
        CustomerEntity.init();
        final LazyCache<List<CustomerEntity>> customersList = CustomerEntity.getCustomersList();
        final List<CustomerEntity> customerEntities = customersList.get();
        customerEntities.forEach(new Consumer<CustomerEntity>() {
                                     @Override
                                     public void accept(CustomerEntity customerEntity) {
                                         System.out.println(customerEntity);
                                     }
                                 }
        );
    }
//
}
