package jpa;

import jpa.converters.ProcessTypeConverter;

import javax.persistence.*;

/**
 * Created by kadir.basol on 25.2.2016.
 */
@Entity
@Table(name = "process", schema = "dbo", catalog = "jdepo")
public class ProcessEntity {

    @Id
    private int id;

    private Integer partiNo;

    private Integer productId;

    private Integer depotId;

    private Integer processType;

    private Integer companyId;

    private Long processAmount;

    private String  aciklama;

    public ProcessEntity() {

    }

    @Basic
    @Column(name = "partNo" , nullable = false)
    public Integer getPartiNo() {
        return partiNo;
    }

    public void setPartiNo(Integer partiNo) {
        this.partiNo = partiNo;
    }

    public ProcessEntity( Integer productId, Integer depotId, ProcessType processType, Integer companyId, Long processAmount, String description) {
        this.productId = productId;
        this.depotId = depotId;
        this.processType = processType.getValue();
        this.companyId = companyId;
        this.processAmount = processAmount;
        this.aciklama = description;
    }

    public ProcessEntity( Integer partiNo , Integer productId, Integer depotId, ProcessType processType, Integer companyId, Long processAmount, String description) {
        this.productId = productId;
        this.depotId = depotId;
        this.processType = processType.getValue();
        this.companyId = companyId;
        this.processAmount = processAmount;
        this.aciklama = description;
        this.partiNo = partiNo;
    }

    @Column(name = "description", nullable = true)
    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "productId", nullable = true)
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "depotId", nullable = false)
    public Integer getDepotId() {
        return depotId;
    }

    public void setDepotId(Integer depotId) {
        this.depotId = depotId;
    }

    @Basic
    @Column(name = "processType", nullable = false)
    @Convert(converter = ProcessTypeConverter.class)
    public Integer getProcessType() {
        return processType;
    }

    public void setProcessType(Integer processType) {
        this.processType = processType;
    }

    @Basic
    @Column(name = "companyId", nullable = false)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "processAmount", nullable = false)
    public Long getProcessAmount() {
        return processAmount;
    }

    public void setProcessAmount(Long processAmount) {
        this.processAmount = processAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessEntity that = (ProcessEntity) o;

        if (id != that.id) return false;
        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (depotId != null ? !depotId.equals(that.depotId) : that.depotId != null) return false;
        if (processType != that.processType) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (processAmount != null ? !processAmount.equals(that.processAmount) : that.processAmount != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (depotId != null ? depotId.hashCode() : 0);
        result = 31 * result + (processType != null ? processType.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (processAmount != null ? processAmount.hashCode() : 0);
        return result;
    }
}
