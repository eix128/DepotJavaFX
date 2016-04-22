package db;

import com.google.inject.Guice;
import com.google.inject.Injector;
import globals.GlobalDatas;
import gui.jpa.ProcessItem;
import jpa.CustomerEntity;
import jpa.DepotsEntity;
import jpa.ProcessEntity;
import jpa.ProductsEntity;
import jpa.converters.enums.PriceType;
import jpa.converters.enums.ProcessType;
import jpa.converters.enums.UnitType;
import kernel.actors.DepotManager;
import kernel.actors.SQLServices;
import kernel.events.ConnectionException;
import kernel.network.DBManager;
import language.LangUtils;
import main.AgentFinderModule;
import org.javatuples.Triplet;
import org.junit.Assert;
import org.junit.Test;
import utils.ThermalPrinter;
import utils.guava.LazyCache;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.print.PrintException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
    public final void denemeXXX() throws UnsupportedEncodingException, PrintException {
        StringBuilder stringBuilder = new StringBuilder( );
        stringBuilder.append("^XA\n" +
                "^LH100,150\n" +
                "^CWT,E:TT0003M_.FNT\n" +
                "^CFT,30,30\n" +
                "^CI28\n" +
                "^FT0,0^FH^FDTesting 1 2 3^FS\n" +
                "^FT0,30^FH^FDДо свидания^FS\n" +
                "^FT0,100^B3^FDAAA001^FS\n" +
                "^XZ");

        ThermalPrinter.PrintString(stringBuilder.toString());
    }


    @Test
    public void timeDeneme() throws ConnectionException {
        final Injector value = Guice.createInjector(new AgentFinderModule());
        GlobalDatas.getInstance().setInjector(value);
        final DBManager dbManager = value.getInstance(DBManager.class);
        dbManager.connectNTLM();
        final SQLServices instance = value.getInstance(SQLServices.class);
        final ResourceBundle instance2 = value.getInstance(ResourceBundle.class);
        final String time = instance.getTime();
        System.out.println(time);
    }


    @Test
    public void countTest() throws ConnectionException {
        final Injector value = Guice.createInjector(new AgentFinderModule());
        GlobalDatas.getInstance().setInjector(value);
        final DBManager dbManager = value.getInstance(DBManager.class);
        dbManager.connectNTLM();
        final SQLServices instance = value.getInstance(SQLServices.class);
        long totalUnits = instance.getTotalUnits();

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
    public void testApp3() throws ConnectionException {
        final Injector value = Guice.createInjector(new AgentFinderModule());
        GlobalDatas.getInstance().setInjector(value);
        final DBManager dbManager = value.getInstance(DBManager.class);
        dbManager.connectNTLM();

        DepotsEntity depotsEntity = new DepotsEntity();
        depotsEntity.setId(-1);
        ProductsEntity productsEntity = new ProductsEntity();
        productsEntity.setId(-1);
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(-1);
        ProcessType processType = ProcessType.OUTPUT;

        final EntityManager entityManager = dbManager.get();


        final CriteriaBuilder criteriaBuilder = dbManager.getCriteriaBuilder();
        final CriteriaQuery<ProcessEntity> query = criteriaBuilder.createQuery(ProcessEntity.class);

        final Root<ProcessEntity> rootEntry = query.from(ProcessEntity.class);

//        Expression<String> postgresqlCastFunction = cb.function("CAST", String.class, from.<String>get("ID").as(String.class));
//        Predicate<String> p = cb.like(postgresqlCastFunction, "10%");

        CriteriaQuery<ProcessEntity> all = query.select(rootEntry);
        final javax.persistence.criteria.Path<Integer> pathDepotId = rootEntry.get("depotId");
        final javax.persistence.criteria.Path<Integer> pathProcessType = rootEntry.get("processType");
        final javax.persistence.criteria.Path<Integer> pathCompanyId = rootEntry.get("companyId");
        final javax.persistence.criteria.Path<Integer> pathProductId = rootEntry.get("productId");

        ArrayList<Triplet<Expression, ? , Predicate>> arrayList = new ArrayList<Triplet<Expression, ? , Predicate>>();
        if (depotsEntity != null && depotsEntity.getId() != -1) {
            final Predicate equal = criteriaBuilder.equal(pathDepotId, depotsEntity.getId());
            arrayList.add(new Triplet<>(pathDepotId, depotsEntity.getDepotId(), equal));
        }
        if (processType != null && processType != ProcessType.UNKNOWN) {
            final Predicate equal1 = criteriaBuilder.equal(pathProcessType, processType);
            arrayList.add(new Triplet<>(pathProcessType, processType , equal1));
        }
        if (customerEntity != null && customerEntity.getId() != -1) {
            final Predicate equal2 = criteriaBuilder.equal(pathCompanyId, customerEntity.getId());
            arrayList.add(new Triplet<>(pathCompanyId, customerEntity.getId(), equal2));
        }

        if (productsEntity != null && productsEntity.getId() != -1) {
            final Predicate equal3 = criteriaBuilder.equal(pathProductId, productsEntity.getId());
            arrayList.add(new Triplet<>(pathProductId, productsEntity.getId(), equal3));
        }

        if (arrayList.size() == 0) {
            return;
        }


        Expression expression = null;
        for (int i = 0; i < arrayList.size(); i++) {
            final Triplet<Expression, ? , Predicate> objects = arrayList.get(i);
            final Expression value0 = objects.getValue0();
            Object value1 = objects.getValue1();
            if(value1 instanceof ProcessType) {
                value1 = (ProcessType)value1;
            } else {
                value1 = (Integer)value1;
            }

            if (expression != null)
                expression = criteriaBuilder.and(criteriaBuilder.equal(value0, value1), expression);
            else
                expression = criteriaBuilder.equal(value0, value1);
        }
//        final Predicate and = criteriaBuilder.and(equal, equal1);
//        final Predicate and1 = criteriaBuilder.and(and, equal2);
//
//        final Predicate and2 = criteriaBuilder.and(and1, equal3);

        final CriteriaQuery<ProcessEntity> where = all.where(expression);
        final TypedQuery<ProcessEntity> query1 = entityManager.createQuery(where);
        final List<ProcessEntity> resultList = query1.getResultList();
        final LangUtils valueInstance = value.getInstance(LangUtils.class);
        final List<ProcessItem> collect = resultList.parallelStream().map(new Function<ProcessEntity, ProcessItem>() {
            @Override
            public ProcessItem apply(ProcessEntity processEntity) {
                final ProcessItem from = ProcessItem.from(valueInstance,processEntity);
                return from;
            }
        }).collect(Collectors.toList());
        for (ProcessItem processItem : collect) {
            System.out.println(processItem);
        }

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
        query.setParameter("UnitsType", UnitType.BOX.getValue());
        query.setParameter("Price", 10);
        query.setParameter("PriceType", PriceType.TL.getValue());
        query.setParameter("Description", "Deneme 1 2 3");
        query.setParameter("PartNo", 45L);
        query.setParameter("CompanyId", 1);
        query.setParameter("DepotId", 1);
        query.setParameter("ProductId", 1);
        query.setParameter("CompanyUserId", 1);

        query.execute();
        Integer sum = (Integer) query.getOutputParameterValue("Success");
        String exception1 = (String) query.getOutputParameterValue("Exception");
        Object processOut = query.getOutputParameterValue("ProcessOut");
        System.out.println(exception1);
        final int i = (int) (Math.random() * Integer.MAX_VALUE);
        query.setParameter("Units", 31L);
        query.setParameter("ProductId", 2);
        query.execute();
        query.setParameter("ProductId", 1);
        query.setParameter("Units", 31L);
        query.execute();
        query.setParameter("Units", 21L);
        query.setParameter("PartNo", i1);
        query.setParameter("UnitsType", UnitType.BOX.getValue());
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
            query.setParameter("DepotId", 1);
            query.setParameter("ProductId", 1);
            query.setParameter("Price", 1);
            query.setParameter("PriceType", PriceType.TL.getValue());
            query.setParameter("Units", 45L);
            query.setParameter("UnitsType", UnitType.BOX.getValue());
            query.setParameter("Description", "Deneme 1 2 3");
            query.setParameter("PartNo", instance.generatePartNo());
            query.setParameter("CompanyUserId", 9999);
            query.execute();
            sum = (Integer) query.getOutputParameterValue("Success");
            String value1 = (String) query.getOutputParameterValue("Exception");
            System.out.println(sum);
            System.out.println(value1);

            query.setParameter("Units", 21L);
            query.setParameter("PartNo", i);
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
