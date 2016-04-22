package jpa;

import jpa.converters.ProcessTypeConverter;
import jpa.converters.enums.ProcessType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by kadir.basol on 21.3.2016.
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
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = Long.class, name = "ProcessOut" ),
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
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = Long.class, name = "ProcessOut"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = Integer.class, name = "Success"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "Exception")
                }
        )
})
@Entity
@Table(name = "process", schema = "dbo", catalog = "jdepo")
public class ProcessEntity implements Comparable<Long> {

    private Long        id;
    private Integer     depotId;
    private Integer     productId;
    private ProcessType processType;
    private Integer     companyId;
    private Integer     companyUserId;
    private Long        units;
    private Byte        unitType;
    private String      description;
    private Long        partNo;
    private Integer     ownerId;
    private Date        actionDate;
    private Long        price;
    private Byte        priceType;

    private int         index;

    public ProcessEntity() {
    }

    @Basic
    @Column(name = "actionDate" , nullable = false)
    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public ProcessEntity(Integer depotId, ProcessType processType, Integer companyId,
                         Integer companyUserId, Long processAmount, Byte processAmountType, String description, Long partNo, Integer ozidasUserId,
                         Date actionDate) {
        this.depotId = depotId;
        this.processType = processType;
        this.companyId = companyId;
        this.companyUserId = companyUserId;
        this.units = processAmount;
        this.unitType = processAmountType;
        this.description = description;
        this.partNo = partNo;
        this.ownerId = ozidasUserId;
        this.actionDate = actionDate;
    }

    @Column(name = "companyUserid")
    public Integer getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(Integer companyUserId) {
        this.companyUserId = companyUserId;
    }

    @Basic
    @Column(name = "ownerid" , nullable = false)
    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ozidasUserId) {
        this.ownerId = ozidasUserId;
    }

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "productid", nullable = false)
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "depotid", nullable = false)
    public Integer getDepotId() {
        return depotId;
    }

    public void setDepotId(Integer depotId) {
        this.depotId = depotId;
    }


    @Basic
    @Column(name = "companyid", nullable = false)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "units", nullable = false)
    public Long getUnits() {
        return units;
    }

    public void setUnits(Long processAmount) {
        this.units = processAmount;
    }

    @Basic
    @Column( name = "unitsType", nullable = false , columnDefinition = "TINYINT" )
//    @Convert(converter = jpa.converters.ProcessAmountTypeConverter.class )
    public Byte getUnitType() {
        return unitType;
    }

    public void setUnitType(Byte processAmountType) {
        this.unitType = processAmountType;
    }

    @Basic
    @Column(name = "partNo", nullable = false)
    public Long getPartNo() {
        return partNo;
    }

    public void setPartNo(Long partNo) {
        this.partNo = partNo;
    }

    @Column(name = "price")
    public Long getPrice() {
        return price;
    }


    public void setPrice(Long price) {
        this.price = price;
    }

    @Column(name = "priceType", columnDefinition = "TINYINT")
    public Byte getPriceType() {
        return priceType;
    }

    public void setPriceType(Byte priceType) {
        this.priceType = priceType;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessEntity that = (ProcessEntity) o;

        if (id != that.id) return false;
        if (depotId != null ? !depotId.equals(that.depotId) : that.depotId != null) return false;
        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (processType != null ? !processType.equals(that.processType) : that.processType != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (companyUserId != null ? !companyUserId.equals(that.companyUserId) : that.companyUserId != null)
            return false;
        if (units != null ? !units.equals(that.units) : that.units != null) return false;
        if (unitType != null ? !unitType.equals(that.unitType) : that.unitType != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (partNo != null ? !partNo.equals(that.partNo) : that.partNo != null) return false;
        if (ownerId != null ? !ownerId.equals(that.ownerId) : that.ownerId != null) return false;
        if (actionDate != null ? !actionDate.equals(that.actionDate) : that.actionDate != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        return priceType != null ? priceType.equals(that.priceType) : that.priceType == null;

    }

    @Override
    public int hashCode() {
        Long result = id;
        return result.hashCode();
    }

    @Column(name = "processType")
    @Convert(converter = ProcessTypeConverter.class)
    public ProcessType getProcessType() {
        return processType;
    }

    public void setProcessType(ProcessType processType) {
        this.processType = processType;
    }

    @Override
    public int compareTo(Long o) {
        Long id = this.getId();
        return Long.compare(id,o);
    }

    @Transient
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
