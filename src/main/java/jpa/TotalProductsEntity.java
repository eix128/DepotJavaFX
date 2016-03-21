package jpa;

import javax.persistence.*;

/**
 * Created by kadir.basol on 21.3.2016.
 */
@Entity
@Table(name = "totalProducts", schema = "dbo", catalog = "jdepo")
public class TotalProductsEntity {
    private int productId;
    private Integer companyId;
    private Integer depotId;
    private Integer totalBox;

    @Id
    @Column(name = "productId", nullable = false)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "companyId", nullable = true)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "depotId", nullable = true)
    public Integer getDepotId() {
        return depotId;
    }

    public void setDepotId(Integer depotId) {
        this.depotId = depotId;
    }

    @Basic
    @Column(name = "totalBox", nullable = true)
    public Integer getTotalBox() {
        return totalBox;
    }

    public void setTotalBox(Integer totalBox) {
        this.totalBox = totalBox;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TotalProductsEntity that = (TotalProductsEntity) o;

        if (productId != that.productId) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (depotId != null ? !depotId.equals(that.depotId) : that.depotId != null) return false;
        if (totalBox != null ? !totalBox.equals(that.totalBox) : that.totalBox != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productId;
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (depotId != null ? depotId.hashCode() : 0);
        result = 31 * result + (totalBox != null ? totalBox.hashCode() : 0);
        return result;
    }
}
