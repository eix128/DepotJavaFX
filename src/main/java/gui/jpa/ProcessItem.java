package gui.jpa;

import com.google.inject.Inject;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import jpa.*;
import jpa.converters.enums.PriceType;
import jpa.converters.enums.ProcessType;
import language.LangUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Kadir on 4/3/2016.
 */
public class ProcessItem implements Serializable,Comparable<ProcessItem> {

    private SimpleLongProperty                      id;

    private SimpleStringProperty                    depot;
    private SimpleStringProperty                    product;
    private SimpleStringProperty                    company;
    private SimpleStringProperty                    owner;
    private SimpleStringProperty                    companyUserId;

    private SimpleObjectProperty<ProcessType>       processType;
    private SimpleObjectProperty<PriceType>         priceType;

    private SimpleLongProperty                      units;
    private SimpleLongProperty                      partNo;
    private SimpleLongProperty                      price;

    private SimpleObjectProperty<LocalDate>         actionDate;

    private SimpleObjectProperty<LocalTime>         actionTime;
    private SimpleStringProperty                    description;

    private Integer                                 index;


    private LangUtils langUtils;


    @Inject
    public ProcessItem(LangUtils langUtils) {
        id = new SimpleLongProperty();
        id.set(-1);
        this.langUtils = langUtils;
        depot = new SimpleStringProperty();
        product = new SimpleStringProperty();
        company = new SimpleStringProperty();
        owner = new SimpleStringProperty();
        companyUserId = new SimpleStringProperty();
        processType = new SimpleObjectProperty<ProcessType>();
//        unitType = new SimpleStringProperty();
        priceType = new SimpleObjectProperty<PriceType>();
        units = new SimpleLongProperty();
        partNo = new SimpleLongProperty( );
        price = new SimpleLongProperty( );
//        final LangUtils langUtils = GlobalDatas.getInstance().getInjector().getInstance(LangUtils.class);
        actionDate = new SimpleObjectProperty<LocalDate>( ) {
            @Override
            public String toString() {
                final LocalDate value = actionDate.getValue();
                return value.getDayOfMonth() + "/" + langUtils.getMonthByIndex( value.getMonthValue() - 1 ) + "/" + value.getYear();
            }
        };
        description = new SimpleStringProperty( );
        actionTime = new SimpleObjectProperty<LocalTime>( ) {
            @Override
            public String toString() {
                final LocalTime value = actionTime.getValue();
                String time = value.getHour() + ":" + value.getMinute();
                return time;
            }
        };
        this.index = new Integer(  0  );
    }

    @Inject
    public final void init() {

    }


    public static final ProcessItem from( LangUtils langUtils , ProcessEntity processEntity) {
        ProcessItem processItem = new ProcessItem( langUtils );
//                                processItem.setId(processItem.getId());
        ConcurrentHashMap<Integer, String> depotsMap = DepotsEntity.getDepotsMap();
        ConcurrentHashMap<Integer, CustomerEntity> customersMap = CustomerEntity.getCustomerById();
        ConcurrentHashMap<Integer, String> companyUsersMap = CompanyUsersEntity.getCompanyUsersMap();
        ConcurrentHashMap<Integer, String> productsMap = ProductsEntity.getProductsMap();

        processItem.setDepot(depotsMap.get(processEntity.getDepotId()));

        Date actionDate = processEntity.getActionDate();
        final ZonedDateTime zonedDateTime = actionDate.toInstant().atZone(ZoneId.systemDefault());

        processItem.setActionDate( zonedDateTime.toLocalDate() );
        CustomerEntity s1 = customersMap.get(processEntity.getCompanyId());
        processItem.setCompany(s1.getTitle());
        processItem.setDescription(processEntity.getDescription());
        processItem.setPartNo(processEntity.getPartNo());
        processItem.setPrice(processEntity.getPrice());
        String s = productsMap.get(processEntity.getProductId());
        processItem.setProduct( s );
        processItem.setCompanyUserId(companyUsersMap.get(processEntity.getCompanyUserId()));
        processItem.setUnits(processEntity.getUnits());
        processItem.setPrice(processEntity.getPrice());
        processItem.setDescription(String.valueOf(processEntity.getDescription()));
        processItem.setIndex(processEntity.getIndex());
        processItem.setProcessType(processEntity.getProcessType());
        processItem.setActionTime( zonedDateTime.toLocalTime() );
        processItem.setId( processEntity.getId() );

        return processItem;
    }


    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getDepot() {
        return depot.get();
    }

    public SimpleStringProperty depotProperty() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot.set(depot);
    }

    public String getProduct() {
        return product.get();
    }

    public SimpleStringProperty productProperty() {
        return product;
    }

    public void setProduct(String product) {
        this.product.set(product);
    }

    public String getCompany() {
        return company.get();
    }

    public SimpleStringProperty companyProperty() {
        return company;
    }

    public void setCompany(String company) {
        this.company.set(company);
    }

    public String getOwner() {
        return owner.get();
    }

    public SimpleStringProperty ownerProperty() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    public String getCompanyUserId() {
        return companyUserId.get();
    }

    public SimpleStringProperty companyUserIdProperty() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId.set(companyUserId);
    }

    public ProcessType getProcessType() {
        return processType.get();
    }

    public SimpleObjectProperty<ProcessType> processTypeProperty() {
        return processType;
    }

    public void setProcessType(ProcessType processType) {
        this.processType.set(processType);
    }

    public PriceType getPriceType() {
        return priceType.get();
    }

    public SimpleObjectProperty<PriceType> priceTypeProperty() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType.set(priceType);
    }

    public long getUnits() {
        return units.get();
    }

    public SimpleLongProperty unitsProperty() {
        return units;
    }

    public void setUnits(long units) {
        this.units.set(units);
    }

    public long getPartNo() {
        return partNo.get();
    }

    public SimpleLongProperty partNoProperty() {
        return partNo;
    }

    public void setPartNo(long partNo) {
        this.partNo.set(partNo);
    }

    public long getPrice() {
        return price.get();
    }

    public SimpleLongProperty priceProperty() {
        return price;
    }

    public void setPrice(long price) {
        this.price.set(price);
    }

    public LocalDate getActionDate() {
        return actionDate.get();
    }

    public SimpleObjectProperty<LocalDate> actionDateProperty() {
        return actionDate;
    }

    public void setActionDate(LocalDate actionDate) {
        this.actionDate.set(actionDate);
    }

    public LocalTime getActionTime() {
        return actionTime.get();
    }

    public SimpleObjectProperty<LocalTime> actionTimeProperty() {
        return actionTime;
    }

    public void setActionTime(LocalTime actionTime) {
        this.actionTime.set(actionTime);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public int compareTo(ProcessItem o) {
        return getIndex().compareTo(o.getIndex());
    }


    @Override
    public String toString() {
        return "ProcessItem{" +
                "id=" + id +
                ", depot=" + depot +
                ", product=" + product +
                ", company=" + company +
                ", owner=" + owner +
                ", companyUserId=" + companyUserId +
                ", processType=" + processType +
                ", priceType=" + priceType +
                ", units=" + units +
                ", partNo=" + partNo +
                ", price=" + price +
                ", actionDate=" + actionDate +
                ", actionTime=" + actionTime +
                ", description=" + description +
                ", index=" + index +
                ", langUtils=" + langUtils +
                '}';
    }
}
