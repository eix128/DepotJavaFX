package gui.jpa;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kadir on 4/3/2016.
 */
public class ProcessItem implements Comparable<Integer> {
    private SimpleLongProperty   id;
    private SimpleStringProperty depot;
    private SimpleStringProperty product;
    private SimpleStringProperty company;
    private SimpleStringProperty owner;
    private SimpleStringProperty companyUserId;
    private SimpleStringProperty processType;
    private SimpleStringProperty priceType;
    private SimpleStringProperty units;
    private SimpleStringProperty partNo;
    private SimpleStringProperty price;
    private SimpleStringProperty actionDate;
    private SimpleStringProperty actionTime;
    private SimpleStringProperty description;

    private Integer        index;


    public ProcessItem(int index) {
        id = new SimpleLongProperty();

        depot = new SimpleStringProperty();
        product = new SimpleStringProperty();
        company = new SimpleStringProperty();
        owner = new SimpleStringProperty();
        companyUserId = new SimpleStringProperty();
        processType = new SimpleStringProperty();
//        unitType = new SimpleStringProperty();
        priceType = new SimpleStringProperty();
        units = new SimpleStringProperty();
        partNo = new SimpleStringProperty();
        price = new SimpleStringProperty();
        actionDate = new SimpleStringProperty();
        description = new SimpleStringProperty();
        actionTime = new SimpleStringProperty();
        index = new Integer(  index );

    }

    public Long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(Long id) {
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

    public String getProcessType() {
        return processType.get();
    }

    public SimpleStringProperty processTypeProperty() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType.set(processType);
    }

//    public String getUnitType() {
//        return unitType.get();
//    }
//
//    public SimpleStringProperty unitTypeProperty() {
//        return unitType;
//    }
//
//    public void setUnitType(String unitType) {
//        this.unitType.set(unitType);
//    }

    public String getPriceType() {
        return priceType.get();
    }

    public SimpleStringProperty priceTypeProperty() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType.set(priceType);
    }

    public String getUnits() {
        return units.get();
    }

    public SimpleStringProperty unitsProperty() {
        return units;
    }

    public void setUnits(String units) {
        this.units.set(units);
    }

    public String getPartNo() {
        return partNo.get();
    }

    public SimpleStringProperty partNoProperty() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo.set(partNo);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getActionDate() {
        return actionDate.get();
    }

    public SimpleStringProperty actionDateProperty() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate.set(actionDate);
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public String getActionTime() {
        return actionTime.get();
    }

    public SimpleStringProperty actionTimeProperty() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime.set(actionTime);
    }

    @Override
    public int compareTo(Integer o) {
        return index.compareTo(o);
    }
}
