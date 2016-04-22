package kernel.actors;

import jpa.converters.enums.ProcessType;
import jpa.converters.enums.UnitType;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by kadir.basol on 22.3.2016.
 */
public interface SQLServices {

    public String getUserName();
    public int generatePartNo();
    public long getTotalUnits();
    public long getTotalUnitsBy( UnitType unitType );
    public long getTotalUnitsBy(UnitType unitType, ProcessType processType, int depotId , int companyId, int productId);
    public String getTime();
}
