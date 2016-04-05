package kernel.actors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by kadir.basol on 22.3.2016.
 */
public interface SQLServices {

    public String getUserName();
    public int generatePartNo();

}
