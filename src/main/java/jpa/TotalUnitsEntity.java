package jpa;

import javax.persistence.*;

/**
 * Created by kadir.basol on 21.3.2016.
 */
@Entity
@Table(name = "totalUnits", schema = "dbo", catalog = "jdepo")
public class TotalUnitsEntity {
    private int companyId;
    private Byte unitType;
    private Long totalBox;

    public TotalUnitsEntity() {
    }

    public TotalUnitsEntity(int companyId, Long totalBox) {
        this.companyId = companyId;
        this.totalBox = totalBox;
    }

    @Id
    @Column(name = "companyId", nullable = false)
    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "totalUnit", nullable = false)
    public Long getTotalBox() {
        return totalBox;
    }

    public void setTotalBox(Long totalBox) {
        this.totalBox = totalBox;
    }


    public Byte getUnitType() {
        return unitType;
    }

    public void setUnitType(Byte unitType) {
        this.unitType = unitType;
    }
}
