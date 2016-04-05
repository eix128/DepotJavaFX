package globals;

import com.google.inject.Injector;

/**
 * Created by Kadir on 3/26/2016.
 */
public class GlobalDatas {

    private static GlobalDatas instance = new GlobalDatas();

    private Injector injector;

    public static GlobalDatas getInstance() {
        return instance;
    }

    public final void setInjector(Injector injector) {
        this.injector = injector;
    }

    public Injector getInjector() {
        return injector;
    }
}
