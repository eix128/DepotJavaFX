package jpa;

import javax.persistence.*;

/**
 * Created by kadir.basol on 4/20/2016.
 */
@Entity
@Table(name = "partNoIndex", schema = "dbo", catalog = "jdepo")
public class PartNoIndexEntity {
    private int companyid;
    private Long partNoid;
    private String partNo;
    private Integer productid;
    private long id;

    @Basic
    @Column(name = "companyid")
    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    @Basic
    @Column(name = "partNoid")
    public Long getPartNoid() {
        return partNoid;
    }

    public void setPartNoid(Long partNoid) {
        this.partNoid = partNoid;
    }

    @Basic
    @Column(name = "partNo")
    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    @Basic
    @Column(name = "productid")
    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartNoIndexEntity that = (PartNoIndexEntity) o;

        if (companyid != that.companyid) return false;
        if (id != that.id) return false;
        if (partNoid != null ? !partNoid.equals(that.partNoid) : that.partNoid != null) return false;
        if (partNo != null ? !partNo.equals(that.partNo) : that.partNo != null) return false;
        if (productid != null ? !productid.equals(that.productid) : that.productid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = companyid;
        result = 31 * result + (partNoid != null ? partNoid.hashCode() : 0);
        result = 31 * result + (partNo != null ? partNo.hashCode() : 0);
        result = 31 * result + (productid != null ? productid.hashCode() : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }
}
