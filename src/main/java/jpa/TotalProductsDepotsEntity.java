//package jpa;
//
//import org.eclipse.persistence.annotations.Direction;
//
//import javax.persistence.*;
//
///**
// * Created by kadir.basol on 21.3.2016.
// */
//@Entity
//@Table(name = "totalProductsDepots", schema = "dbo", catalog = "jdepo")
//@NamedQueries(
//        {
//                @NamedQuery(
//                        name = "TotalProductsDepotsEntity.SelectBy",
//                        query = "select t from TotalProductsDepotsEntity t where t.companyId=:companyId and t.depotId = :depotId and t.productId = :productId and t.partNo = :partNo"
//                )
//        }
//)
//@NamedStoredProcedureQueries(
//        @NamedStoredProcedureQuery(name = "kadir",procedureName = "PERSON_SP" , resultClasses = {Void.class} , resultSetMappings = {} ,
//                parameters = {
//                        @StoredProcedureParameter(name = "EMP_ID", type = Double.class  , mode = ParameterMode.IN),
//                        @StoredProcedureParameter(name = "SAL_INCR", type = Long.class , mode = ParameterMode.OUT )}
//        )
//)
//public class TotalProductsDepotsEntity {
//    private long id;
//    private Integer companyId;
//    private Integer productId;
//    private Integer totalBox;
//    private Integer depotId;
//    private Long partNo;
//
//    public TotalProductsDepotsEntity() {
//
//    }
//
//    public TotalProductsDepotsEntity(Integer companyId, Integer productId, Integer totalBox, Integer depotId, Long partNo) {
//        this.companyId = companyId;
//        this.productId = productId;
//        this.totalBox = totalBox;
//        this.depotId = depotId;
//        this.partNo = partNo;
//    }
//
//    @Id
//    @Column(name = "id", nullable = false)
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    @Basic
//    @Column(name = "companyId", nullable = true)
//    public Integer getCompany() {
//        return companyId;
//    }
//
//    public void setCompany(Integer companyId) {
//        this.companyId = companyId;
//    }
//
//    @Basic
//    @Column(name = "productId", nullable = true)
//    public Integer getProduct() {
//        return productId;
//    }
//
//    public void setProduct(Integer productId) {
//        this.productId = productId;
//    }
//
//    @Basic
//    @Column(name = "totalBox", nullable = true)
//    public Integer getTotalBox() {
//        return totalBox;
//    }
//
//    public void setTotalBox(Integer totalBox) {
//        this.totalBox = totalBox;
//    }
//
//    @Basic
//    @Column(name = "depotId", nullable = true)
//    public Integer getDepot() {
//        return depotId;
//    }
//
//    public void setDepot(Integer depotId) {
//        this.depotId = depotId;
//    }
//
//    @Basic
//    @Column(name = "partNo", nullable = true)
//    public Long getPartNo() {
//        return partNo;
//    }
//
//    public void setPartNo(Long partNo) {
//        this.partNo = partNo;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        TotalProductsDepotsEntity that = (TotalProductsDepotsEntity) o;
//
//        if (id != that.id) return false;
//        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
//        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
//        if (totalBox != null ? !totalBox.equals(that.totalBox) : that.totalBox != null) return false;
//        if (depotId != null ? !depotId.equals(that.depotId) : that.depotId != null) return false;
//        if (partNo != null ? !partNo.equals(that.partNo) : that.partNo != null) return false;
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = (int) (id ^ (id >>> 32));
//        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
//        result = 31 * result + (productId != null ? productId.hashCode() : 0);
//        result = 31 * result + (totalBox != null ? totalBox.hashCode() : 0);
//        result = 31 * result + (depotId != null ? depotId.hashCode() : 0);
//        result = 31 * result + (partNo != null ? partNo.hashCode() : 0);
//        return result;
//    }
//}
